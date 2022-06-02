----------------------------------------------------------------------
-- File: MemProgVectores.vhd
-- Description: Template for the prorammemory MIPS
-- Date last modification: 2019-03-29

-- Authors: Sofía Martínez (2019), Alberto Sánchez (2012-2018), Ángel de Castro (2010-2015)
-- Sbuject: Sbuject: C.E. 1st grade
-- Laboratory group:
-- Theory group:
-- Task: 4
-- Exercise: 3
----------------------------------------------------------------------


library IEEE;
use IEEE.std_logic_1164.ALL;
use IEEE.numeric_std.all;

entity MemProgVectores is
	port (
		MemProgAddr : in unsigned(31 downto 0); -- Address of the program memory
		MemProgData : out unsigned(31 downto 0) -- Operation code
	);
end MemProgVectores;

architecture Simple of MemProgVectores is

begin

	LecturaMemProg: process(MemProgAddr)
	begin
		-- The memory gives back a value for each address.
		-- These values are the program codes for each instruction,
		-- which are located in its address.
		case MemProgAddr is
		-------------------------------------------------------------
		-- Only introduce changes from here!!		
			
			when X"00000000" => MemProgData <= X"8c042000";			
			when X"00000004" => MemProgData <= X"00842020";		
			when X"00000008" => MemProgData <= X"00842020";		
			when X"0000000c" => MemProgData <= X"00004020";		
			when X"00000010" => MemProgData <= X"10880008";		
			when X"00000014" => MemProgData <= X"8d0a2004";		
			when X"00000018" => MemProgData <= X"8d0b201c";		
			when X"0000001c" => MemProgData <= X"016b5820";		
			when X"00000020" => MemProgData <= X"016b5820";		
			when X"00000024" => MemProgData <= X"014b5820";		
			when X"00000028" => MemProgData <= X"ad0b2034";		
			when X"0000002c" => MemProgData <= X"21080004";		
			when X"00000030" => MemProgData <= X"08000004";		
			when X"00000034" => MemProgData <= X"0800000d";	
			
						
		-- Until here!	
		---------------------------------------------------------------------	
			
			when others => MemProgData <= X"00000000"; -- Rest of empty memory
		end case;
	end process LecturaMemProg;

end Simple;

