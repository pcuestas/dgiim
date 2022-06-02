from random import seed, randint, shuffle
from matplotlib.pyplot import show, savefig, subplots, subplot, figure, close
from matplotlib import gridspec
from matplotlib.transforms import Bbox
import numpy as np
from numpy.core.fromnumeric import clip
from os.path import exists
from os import makedirs
from pandas import read_excel

NIA_OFFSET = 1965
DELETES = 10


class Packages:
    def __init__(
        self,
        n_packages=20,
        times=(60, 100),
        sizes=(7, 10),
    ):
        inits = list(range(times[0], times[1]))
        shuffle(inits)
        inicios = inits[: n_packages + DELETES]
        longitudes = [randint(sizes[0], sizes[1]) for _ in range(n_packages + DELETES)]
        packs = list(zip(inicios, longitudes))
        packs.sort()
        delete_pos = randint(1, n_packages)
        for _ in range(DELETES):
            packs.pop(delete_pos)
        self.packages = {}
        for i, p in enumerate(packs):
            self.packages[p[0]] = [i, p[1]]


class Bucket(Packages):
    def __init__(
        self,
        ticks=10,
        n_packages=20,
        times=(60, 100),
        sizes=(7, 10),
    ):
        self.bucket = 0
        self.time = 0
        self.ticks_max = ticks
        if self.ticks_max < sizes[1]:
            self.ticks_max = sizes[1]
        self.ticks = 0
        super().__init__(n_packages, times, sizes)

    def ask_token(self):
        self.time += 1
        self.ticks = self.ticks + 1
        if self.ticks == self.ticks_max:
            self.ticks = 0
            self.bucket += 1
        return self.bucket


class Queue(Bucket):
    def __init__(
        self,
        queue_size=10,
        ticks=10,
        n_packages=20,
        times=(60, 100),
        sizes=(7, 10),
    ):
        self.next_exit = 0
        self.queue = []
        self.lost = []
        self.exits = []
        self.queue_size = queue_size
        super().__init__(ticks, n_packages, times, sizes)

    def tick(self):
        n_bucket = self.ask_token()
        if self.time in self.packages:
            if len(self.queue) < self.queue_size:
                self.queue.append(self.packages[self.time])
            else:
                self.lost.append(self.packages[self.time])
        if n_bucket > 0 and len(self.queue) > 0 and self.time >= self.next_exit:
            pack_exit = self.queue.pop(0)
            self.next_exit = self.time + pack_exit[1]
            self.bucket = self.bucket - 1
            self.exits.append([self.time, pack_exit[0], pack_exit[1], self.bucket])


class BucketProblem(Queue):
    def __init__(
        self,
        time_end=500,
        queue_size=10,
        ticks=10,
        n_packages=20,
        times=(20, 99),
        sizes=(3, 8),
    ):
        self.time_end = time_end
        self.queue_size = queue_size
        self.ticks = ticks
        self.n_packages = n_packages
        self.times = times
        self.sizes = sizes

    def build(self, nia):
        seed(nia + NIA_OFFSET)
        self.time_end = self.time_end
        first = 0
        while True:
            super().__init__(
                self.queue_size, self.ticks, self.n_packages, self.times, self.sizes
            )
            for _ in range(self.time_end):
                self.tick()
            if len(self.lost) > 0:
                break


class Build:
    def __init__(self, nia_array, queue_size=10):
        self.nia_array = nia_array
        self.solutions = {}
        self.queue_size = queue_size
        self.ticks = 10
        self.build_solutions()

    def build_solutions(self):
        for nia in self.nia_array:
            self.ticks = randint(8, 12)
            bucket = BucketProblem(queue_size=self.queue_size, ticks=self.ticks)
            bucket.build(nia)
            self.solutions[nia] = [
                bucket.packages,
                bucket.exits,
                bucket.lost,
                self.ticks,
            ]

    def full_data(self):
        strs = ""
        for nia in self.solutions:
            strs = strs + ("\033[37mNIA = %d\n" % nia)
            strs = strs + "\033[32mPackages: \n"
            for pack_t in self.solutions[nia][0]:
                strs = strs + (
                    "\t Id = %d \tT recepción = %d\tlen = %d\n"
                    % (
                        self.solutions[nia][0][pack_t][0],
                        pack_t,
                        self.solutions[nia][0][pack_t][1],
                    )
                )
            strs = strs + "\033[36m\tTime   \tId   \tlen  \tTokens\n"
            for exits in self.solutions[nia][1]:
                strs = strs + ("\t%d\t%d\t%d\t%d\t\n" % tuple(exits))
            strs = strs + "\033[31m\tId   \tlen\n"
            for lost in self.solutions[nia][2]:
                strs = strs + ("\t%d\t%d\n" % tuple(lost))

            strs = strs + "\033[00m----------------------------------------\n"
        return strs

    def __str__(self):
        strs = ""
        for nia in self.solutions:
            strs = strs + ("%d | " % nia)
            for exits in self.solutions[nia][1]:
                strs = strs + ("%d:%d-" % (exits[1], exits[0]))
            strs = strs[:-1] + " | "
            for lost in self.solutions[nia][2]:
                strs = strs + ("%d-" % lost[0])
            strs = strs[:-1] + "\n"
        return strs

    def _build_image(self, nia):
        fig = figure(figsize=(6.6, 7))
        spec = gridspec.GridSpec(
            ncols=1, nrows=3, hspace=0, height_ratios=[2.6, 0.5, 1.9]
        )

        ax0 = fig.add_subplot(spec[0])
        ax0.set_axis_off()

        ax0.text(0, 0.9, "Nia: %d" % nia, fontsize=13, color="red", weight="bold")
        enunciado = """
        Se dispone de un router que va a enviar los paquetes de acuerdo una política basada
en un balde de fichas. Las características de este balde son las siguientes:

        - Es un balde con un límite de fichas tan alto que no se tendrá en cuenta ningún 
        límite de fichas.
        - Inicialmente el balde no tiene ninguna ficha.
        - La primera ficha cae cuando transcurre el primer periodo de espera.
        - Cae una ficha cada %d unidades de tiempo.

        Así mismo el router dispone de un buffer para recibir los paquetes que enviará a la
red. Este buffer tiene una capacidad para %d paquetes. Si el buffer está lleno y se recibe un
paquete se aplicará la política de tail drop para descartar un paquete.

        En la tabla adjunta se indican los paquetes que recibe el router desde diversas 
fuentes identificándolos con un id cuya finalidad exclusiva es poder realizar referencias a 
ese paquete en este problema; con un instante en unidades de tiempo que indica cuándo 
recibe el paquete el router; y una longitud en unidades de tiempo que indica cuántas 
unidades de tiempo necesita el router para enviar dicho paquete a la red.
""" % (
            self.solutions[nia][3],
            self.queue_size,
        )
        ax0.text(0, 0, enunciado, clip_on=True, fontsize=8)

        ax1 = fig.add_subplot(spec[1])
        ax1.axis([0, 10, 0, 10])
        ax1.set_axis_off()
        aux1 = []
        aux2 = []
        aux3 = []
        row_labels = ["Identificador", "T llegada", "Tamaño"]
        for time in self.solutions[nia][0]:
            aux1.append(self.solutions[nia][0][time][0])
            aux2.append(time)
            aux3.append(self.solutions[nia][0][time][1])
        data = [aux1, aux2, aux3]

        table = ax1.table(
            cellText=data,
            rowLabels=row_labels,
            loc="center",
            cellLoc="center",
            colWidths=[0.05] * len(data[0]),
        )
        table.auto_set_font_size(False)
        table.set_fontsize(6)
        table.scale(0.85, 0.85)

        ax2 = fig.add_subplot(spec[2])
        ax2.set_axis_off()
        enunciado2 = """
        Calcula el instante de salida de cada paquete y si se han perdido indica cuáles se 
han perdido. Para ello la respuesta en moodle debe ser de la forma, sin espacios: 
0:16-1:21-2:38:... Donde el primer número indica el paquete y el segundo su instante de 
salida.
        
        Los paquetes perdidos en caso de haberlos se indicarán con una X en lugar del 
instante de salida, es decir de la forma 0:16-1:X-2:31- ... Así mismo debes 
enviar fotografía del problema resuelto en papel.

        Es importante recordar que sólo hay un canal de salida y que, por tanto, hasta 
que no ha terminado de enviarse un paquete no se puede enviar el siguiente. Para ello es 
necesario tener en cuenta la longitud en tiempo de los paquetes.

        Como tiempo de salida se indicará en instante en que empieza a salir el paquete. 
El orden de evaluación en coincidencias temporales es: 1º ficha, 2º cola, 3º salida.
"""
        ax2.text(0, 0, enunciado2, clip_on=True, fontsize=8)
        return fig

    def save_images(self, dir):
        if not exists(dir):
            makedirs(dir)

        for nia in self.nia_array:
            fig = self._build_image(nia)
            savefig(dir + "/%d_3.png" % int(nia))
            close(fig)

    def show_images(self):
        for nia in self.nia_array:
            fig = self._build_image(nia)
            show()
            close(fig)


b = Build([34567])
print(b)
b.show_images()
