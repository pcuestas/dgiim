----------------------------------------------------------------------
-- File: MemProgSuma.vhd
-- Description: Memory of the program for MIPS. It has addition operations between a register and immediate data
-- Date last modification: 2019-02-22

-- Authors: Sof�a Mart�nez (2019),Alberto S�nchez (2012, 2017), �ngel de Castro (2010) 
-- Sbuject: Sbuject: C.E. 1st grade
-- Laboratory group:
-- Theory group:
-- Task: 2
-- Exercise: 3
-----------------------------------------------------------------------


library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;


entity MemProgSuma is
	port (
		MemProgAddr : in unsigned(31 downto 0); -- Address for the program memory
		MemProgData : out unsigned(31 downto 0) -- Operation code
	);
end MemProgSuma;

architecture Simple of MemProgSuma is

begin

	LecturaMemProg: process(MemProgAddr)
	begin
		-- The memory gives back a value for every address.
		-- These values are the codes of program for every instruction, 
		-- being every one in its address.
		case MemProgAddr is
			when X"00000000" => MemProgData <= X"2001000a";		-- R1 = R0 + 10
			when X"00000004" => MemProgData <= X"20220005";		-- R2 = R1 + 5
			when X"00000008" => MemProgData <= X"20430019";		-- R3 = R2 + 25	
			when X"0000000C" => MemProgData <= X"20000005";		-- R0 = R0 + 5
			when X"00000010" => MemProgData <= X"2064FFFB";		-- R4 = R3 -  5
			when others => MemProgData <= X"00000000"; 				-- Rest of empty memory
		end case;
	end process LecturaMemProg;

end Simple;
