#!/usr/bin/env python3

from random import randint, shuffle, seed, gauss
import matplotlib.pyplot as plt
from matplotlib.ticker import MultipleLocator, FormatStrFormatter, AutoMinorLocator

NIA_INIT = 1972


class SoundEmit:
    def __init__(
        self,
        nia=0,
        n_pack=12,
        n_silences_min=3,
        n_silences_max=5,
        mean_delay=10,
        sigma_delay=5,
        mean_pack_delay=110,
        sigma_pack_delay=15,
        delayed_packages_ratio=0.5,
        K=4,
        u=0.1,
    ):
        seed(nia + NIA_INIT)
        self.nia = nia
        self.n_pack = n_pack
        self.n_silences_min = n_silences_min
        self.n_silences_max = n_silences_max
        self.n_silences = n_silences_min
        self.mean_delay = mean_delay
        self.sigma_delay = sigma_delay
        self.mean_pack_delay = mean_pack_delay + round(gauss(20, 10), 2)
        self.sigma_pack_delay = sigma_pack_delay
        self.delayed_packages = int(
            round(self.n_pack * delayed_packages_ratio, 0)
        ) + randint(-1, 1)
        self.K = K + randint(-2, 2)
        self.u = round(u + randint(-(int(10 * u) - 1), int(10 * u) + 1) / (u * 100), 2)

        self._silences()
        self._emision()
        self._reception()
        self._select_fixed()
        self._graph_points()
        self.fixed_solution()
        self.adaptative_solution()

    def _silences(self):
        self.n_silences = randint(self.n_silences_min, self.n_silences_max)
        a = [_ for _ in range(1, self.n_pack)]
        shuffle(a)
        self.silences_pos = a[: self.n_silences]
        self.delays = [
            round(abs(gauss(self.mean_delay, self.sigma_delay)), 2)
            for _ in range(self.n_silences)
        ]

    def _emision(self):
        self.packages_emision = [20 * (_ + 1) for _ in range(0, self.n_pack)]
        for i in range(self.n_silences):
            for j in range(self.silences_pos[i], self.n_pack):
                self.packages_emision[j] = round(
                    self.packages_emision[j] + self.delays[i], 2
                )

    def _reception(self):
        self.fly_delays = [
            abs(round(gauss(self.mean_pack_delay, self.sigma_pack_delay), 2))
            for _ in range(self.n_pack)
        ]
        self.packages_reception = [
            round(self.packages_emision[i] + self.fly_delays[i], 2)
            for i in range(self.n_pack)
        ]

    def _select_fixed(self):
        a = self.fly_delays[:]
        a.sort()
        if a[-self.delayed_packages] - a[-self.delayed_packages - 1] > 2:
            self.fixed = round(
                (a[-self.delayed_packages] + a[-self.delayed_packages - 1]) / 2, 0
            )
        else:
            self.fixed = round(
                (a[-self.delayed_packages] + a[-self.delayed_packages - 1]) / 2, 2
            )
        self.fixed_without_losses = max(self.fly_delays)

    def fixed_solution(self):
        self.fixed_repr = [
            self.packages_emision[i] + self.fixed for i in range(0, self.n_pack)
        ]
        self.fixed_lost = ""
        for repr, recv in zip(self.fixed_repr, self.packages_reception):
            if repr < recv:
                self.fixed_lost += "X"
            else:
                self.fixed_lost += "-"
        return self.fixed_lost

    def adaptative_solution(self):
        d = self.packages_reception[0] - self.packages_emision[0]
        v = 0
        self.adaptative_repr = []
        last = self.fixed

        for i, (emis, recep) in enumerate(
            zip(self.packages_emision, self.packages_reception)
        ):
            d = (1 - self.u) * d + self.u * (recep - emis)
            v = (1 - self.u) * v + self.u * abs(recep - emis - d)
            p = d + self.K * v
            if i in self.silences_pos:
                if emis + p >= last + 20:
                    last = emis + p - 20
            last = last + 20
            self.adaptative_repr.append(last)

        self.lost_adaptative = ""
        for adapt, recept in zip(self.adaptative_repr, self.packages_reception):
            if adapt < recept:
                self.lost_adaptative += "X"
            else:
                self.lost_adaptative += "-"

    def __str__(self):
        str_r = "NIA: " + str(int(self.nia))
        str_r += "\n\nIniciales = \t"
        for i in range(self.n_pack):
            str_r += str(self.packages_emision[i]) + "\t"

        str_r += "\nFinales = \t"
        for i in range(self.n_pack):
            str_r += str(self.packages_reception[i]) + "\t"

        str_r += "\nSilencios = \t"
        for i in range(self.n_pack):
            if i in self.silences_pos:
                str_r += "X"
            else:
                str_r += "-"

        str_r += (
            "\nRetraso Fijo = "
            + str(self.fixed)
            + "\n\nResultados retraso fijo:\n\tReproducción =\t"
        )
        t_repro = [pack + self.fixed for pack in self.packages_emision]

        for pack in t_repro:
            str_r += str(round(pack, 2)) + "\t"

        str_r += "\n\tPaquetes no reproducidos = " + self.fixed_lost + "\n"
        str_r += (
            "\tRetraso mínimo sin pérdidas = "
            + str(round(self.fixed_without_losses, 2))
            + "\n\n"
        )

        str_r += "\nResultados retraso adaptativo:\n\tReproducción =\t"
        for pack in self.adaptative_repr:
            str_r += str(round(pack, 2)) + "\t"

        str_r += "\n\tPaquetes no reproducidos = " + self.lost_adaptative + "\n"

        return str_r

    def print(self):
        str_r = "NIA: " + str(int(self.nia)) + "\n"
        # for i in range(self.n_pack):
        #     if i in self.silences_pos:
        #         str_r += "X"
        #     else:
        #         str_r += "-"
        # str_r += "\n"
        t_repro = [pack + self.fixed for pack in self.packages_emision]

        for pack in t_repro:
            str_r += str(round(pack, 2)) + "-"
        str_r = str_r[:-1] + "\n"

        for pack in self.fixed_lost:
            str_r += str(pack)
        str_r += "\n"

        str_r += str(round(self.fixed_without_losses, 2)) + "\n"

        for pack in self.adaptative_repr:
            str_r += str(round(pack, 2)) + "-"
        str_r = str_r[:-1] + "\n"

        for pack in self.lost_adaptative:
            str_r += str(pack)
        str_r += "\n"

        print(str_r)

    def _graph_points(self):
        self.reproduction_points_y = [0]
        self.reproduction_points_x_send = [0]
        self.reproduction_points_x_recv = [0]
        for i in range(0, self.n_pack):
            self.reproduction_points_y.append(i)
            self.reproduction_points_y.append(i + 1)
            self.reproduction_points_x_send.append(self.packages_emision[i])
            self.reproduction_points_x_send.append(self.packages_emision[i])
            self.reproduction_points_x_recv.append(self.packages_reception[i])
            self.reproduction_points_x_recv.append(self.packages_reception[i])
        self.reproduction_points_y.append(i + 1)
        self.reproduction_points_x_send.append(
            round(self.packages_reception[i] + 20, 0)
        )
        self.reproduction_points_x_recv.append(
            round(self.packages_reception[i] + 20, 0)
        )

    def _plottable_row(self, ax, labels, *data):
        dtaux = []
        for a in zip(*data):
            dtaux.append(list(a))
        tbl = ax.table(cellText=data, rowLabels=labels, bbox=[0.12, 0.6, 0.9, 0.9])

    def plot(self):
        self.figure, ax = plt.subplots(
            4, 1, figsize=(6.4, 10), gridspec_kw={"height_ratios": [1, 0.6, 2.5, 3.5]}
        )
        ax[0].text(0, 1, "NIA = " + str(int(self.nia)))

        ax[0].axis("off")
        ax[1].axis("off")
        ax[3].axis("off")

        ax[2].plot(self.reproduction_points_x_send, self.reproduction_points_y)
        ax[2].plot(self.reproduction_points_x_recv, self.reproduction_points_y)
        ax[2].xaxis.set_major_locator(MultipleLocator(100))
        ax[2].xaxis.set_minor_locator(MultipleLocator(20))
        ax[0].text(
            0,
            0.2,
            "Se tiene un sistema de trasmisión de audio en el que sus primeros paquetes \ncon sus "
            + "tiempos de emisión y recepción pueden verse en la tabla y en la \nfigura adjunta.",
        )

        id = [a + 1 for a in range(self.n_pack)]
        self._plottable_row(
            ax[1],
            ["Id", "Emisión (ms)", "Recepción (ms)"],
            id,
            self.packages_emision,
            self.packages_reception,
        )
        ax[3].text(
            0,
            0,
            """Si tenemos un retraso fijo de %s ms, (r=%s, p=%s) calcula:\n
A) La lista de los instantes en los que se intentaría reproducir dicho 
paquete.
B) Qué paquetes no se van a reproducir y cuáles sí. Exprésalo de la forma 
XX---X-XX- donde la primera X indica que el paquete 1 no ha sido reproducido, 
la segunda X que el segundo tampoco, el guión en la tercera posición implica 
que el paquete 3 sí se ha reproducido (y así sucesivamente).
C) Calcula el retraso fijo mínimo necesario para que todos los paquetes se 
reproduzcan.\n
Tomando como retraso inicial el retraso fijo anterior, K=%s y u=%s calcula:\n
D) La lista de los instantes en los que se intentaría reproducir cada 
paquete.
E) Como en el caso anterior indica con X y guiones qué paquetes se 
reproducen y cuáles no.
"""
            % (
                str(self.fixed),
                str(round(self.packages_reception[0] - self.packages_emision[0], 2)),
                str(
                    round(
                        self.fixed
                        - self.packages_reception[0]
                        + self.packages_emision[0],
                        2,
                    )
                ),
                str(self.K),
                str(self.u),
            ),
        )


def solutions(
    nia_array,
    folder=".",
    added_to_name=None,
    n_pack=12,
    n_silences_min=3,
    n_silences_max=5,
    mean_delay=10,
    sigma_delay=5,
    mean_pack_delay=110,
    sigma_pack_delay=15,
    delayed_packages_ratio=0.5,
    K=4,
    u=0.1,
    calculated_type=["solutions"],
):
    if added_to_name:
        added_to_name = "_{}".format(added_to_name)
    for nia in nia_array:
        S = SoundEmit(
            nia=int(nia),
            n_pack=n_pack,
            n_silences_min=n_silences_min,
            n_silences_max=n_silences_max,
            mean_delay=mean_delay,
            sigma_delay=sigma_delay,
            mean_pack_delay=mean_pack_delay,
            sigma_pack_delay=sigma_pack_delay,
            delayed_packages_ratio=delayed_packages_ratio,
            K=K,
            u=u,
        )
        if "solutions" in calculated_type:
            S.print()
        if "save" in calculated_type:
            S.plot()
            S.figure.savefig("{}/{}{}.png".format(folder, int(nia), added_to_name))
        if "plot" in calculated_type:
            S.plot()
            plt.show()
        plt.close()


solutions(
    [54321],
    folder=".",
    added_to_name="",
    calculated_type=["save", "plot", "solutions"],
)