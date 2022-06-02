----------------------------------------------------------------------
-- File: MemDataVectores.vhd
-- Description: Template of the data memory for MIPS
-- Date last modification: 2019-03-29

-- Authors: Sofía Martínez (2019), Alberto Sánchez (2012-2018), Ángel de Castro (2010)
-- Sbuject: Sbuject: C.E. 1st grade
-- Laboratory group:
-- Theory group:
-- Task: 4
-- Exercise: 1
----------------------------------------------------------------------




library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;

entity MemDataVectores is
	port (
		Clk : in std_logic;
		NRst : in std_logic;
		MemDataAddr : in unsigned(31 downto 0);
		MemDataDataWrite : in signed(31 downto 0);
		MemDataWE : in std_logic;
		MemDataDataRead : out signed(31 downto 0)
	);
end MemDataVectores;

architecture Simple of MemDataVectores is

  -- 4 GB are 1 gigawords, but the simulator does not allow much memory
  -- We leave 64 kB (16 kwords), using the 6 LSB
  type Memoria is array (0 to (2**14)-1) of signed(31 downto 0);
  signal memData : Memoria;

begin

	EscrituraMemProg: process(Clk, NRst)
	begin
	if NRst = '0' then
		-- It is initialized to ceros, except the addresses value which
		-- have a initial valor different to cero (data already charged in memory
		-- from data of the beginning
		for i in 0 to (2**14)-1 loop
			memData(i) <= (others => '0');
		end loop;
		-- Every word occupies 4 bytes
	-------------------------------------------------------------
	-- Only introduce changes from here!!	
		memData((16#00002000#)/4) <= X"00000006";
		memData((16#00002004#)/4) <= X"00000002";
		memData((16#00002008#)/4) <= X"00000004";
		memData((16#0000200c#)/4) <= X"00000006";
		memData((16#00002010#)/4) <= X"00000008";
		memData((16#00002014#)/4) <= X"0000000a";
		memData((16#00002018#)/4) <= X"0000000c";
		memData((16#0000201c#)/4) <= X"ffffffff";
		memData((16#00002020#)/4) <= X"fffffffb";
		memData((16#00002024#)/4) <= X"00000004";
		memData((16#00002028#)/4) <= X"0000000a";
		memData((16#0000202c#)/4) <= X"00000001";
		memData((16#00002030#)/4) <= X"fffffffb";
	-- Until here!	
	---------------------------------------------------------------------	
	elsif rising_edge(Clk) then
		-- It is written in a falling clock edge (middle of the cycle)
		-- and all the signals would be stable
		if MemDataWE = '1' then
			memData(to_integer(MemDataAddr)/4) <= MemDataDataWrite;
		end if;
	end if;
	end process EscrituraMemProg;

	-- Combinational reading always active
	-- Every time a complete word is given back, it occupies 4 bytes
	LecturaMemProg: process(MemDataAddr, memData)
	begin
		-- Low part of the memory. If it exists, it will read
		if MemDataAddr(31 downto 16)=X"0000" then
			MemDataDataRead <= MemData(to_integer(MemDataAddr)/4);
		else -- High part does not exist, zeros are read.
			MemDataDataRead <= (others => '0');
		end if;
	end process LecturaMemProg;

end Simple;

