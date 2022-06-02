----------------------------------------------------------------------
----------------------------------------------------------------------
-- Subject: Computer Structure. GII and DGIIM grade. 1st levl
-- File: MemDataMIPS.vhd
-- Description: Data memory for MIPS
-- Helper file for task 4. Exercise 3. It is used in the first validation of the exercise 3
----------------------------------------------------------------------
----------------------------------------------------------------------


library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;

entity MemDataMIPS is
	port (
		Clk : in std_logic;
		NRst : in std_logic;
		MemDataAddr : in unsigned(31 downto 0);
		MemDataDataWrite : in signed(31 downto 0);
		MemDataWE : in std_logic;
		MemDataDataRead : out signed(31 downto 0)
	);
end MemDataMIPS;

architecture Simple of MemDataMIPS is

  -- 4 GB are 1 gigawords, but the simulator does not allow much memory
  -- We leave 64 kB (16 kwords), using the 6 LSB
  type Memoria is array (0 to (2**14)-1) of signed(31 downto 0);
  signal memData : Memoria;

begin

EscrituraMemData: process(all)
	begin
	if NRst = '0' then
		-- It is initialized to ceros, except the addresses value which
		-- have a initial valor different to cero (data already charged in memory
		-- from data of the beginning)
		for i in 0 to (2**14)-1 loop
			memData(i) <= (others => '0');
		end loop;
		-- Values from .bin
		-- Every word occupies 4 bytes
		-- Código para la escritura de los datos iniciales quedeben ser cargados previamente en memoria antes de la ejecución del programa.
		
		-- Se cargan a partir de una dirección determinada, en MARS dada por la directiva .data. En este caso en la dirección 0x00002000.
		-- Para que se entienda como un índice entero, la dirección 0x2000 se debe escribir como 16#2000#.
		-- Como cada dato ocupa 4 bytes, el índice entero debe ir dividido por 4.
 
--      *************************************************
				memData(16#2000#/4) <= x"0000000A";
				memData(16#2004#/4) <= x"00000009";
				memData(16#2008#/4) <= x"00000009";
--		*************************************************												 
	elsif rising_edge(Clk) then
		-- It is written in a falling clock edge (middle of the cycle)
		-- and all the signals would be stable
		if MemDataWE = '1' then
			memData((to_integer(MemDataAddr)/4)) <= MemDataDataWrite;
		end if;
	end if;
	end process EscrituraMemData;

	-- Combinational reading always active
	-- Every time a complete word is given back, it occupies 4 bytes
	LecturaMemProg: process(MemDataAddr, memData)
	begin
		-- Low part of the memory. If it exists, it will read
		if MemDataAddr(31 downto 16) = x"0000" then
			MemDataDataRead <= MemData((to_integer(MemDataAddr)/4));
		else -- High part does not exist, zeros are read.
			MemDataDataRead <= (others => '0');
		end if;
	end process LecturaMemProg;

end Simple;

