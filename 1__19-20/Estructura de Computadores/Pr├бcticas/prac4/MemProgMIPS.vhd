----------------------------------------------------------------------
-- Subject: Computer Strucuture. GII and DGIIM grade. 1st level
-- File: MemProgMIPS.vhd
-- Description: Program memory for MIPS
-- Date last modification: 2019-03-29
-- Helper file to task 4. Exercise 3. It is used in the first validation.
----------------------------------------------------------------------



library IEEE;
use IEEE.std_logic_1164.ALL;
use IEEE.numeric_std.ALL;
								

entity MemProgMIPS is
	port (
		MemProgAddr : in unsigned(31 downto 0);  -- Address or the program memory 
		MemProgData : out unsigned(31 downto 0)  -- Operation code
	);
end MemProgMIPS;

architecture Simple of MemProgMIPS is

begin

	EscrituraMemProg: process(all)
	begin
		-- The memory gives back a value for each address.
		-- These values are the program codes for each instruction,
		-- which are located in its address.
		case MemProgAddr is
			when X"00000000" => MemProgData <= X"20010004";
			when X"00000004" => MemProgData <= X"3402000f";
			when X"00000008" => MemProgData <= X"30430004";
			when X"0000000C" => MemProgData <= X"2064ffec";
			when X"00000010" => MemProgData <= X"28057fff";
			when X"00000014" => MemProgData <= X"2805ffff";
			when X"00000018" => MemProgData <= X"0081302a";
			when X"0000001C" => MemProgData <= X"8c072000";
			when X"00000020" => MemProgData <= X"8c282000";
			when X"00000024" => MemProgData <= X"8c092008";
			when X"00000028" => MemProgData <= X"01075022";
			when X"0000002C" => MemProgData <= X"10240001";
			when X"00000030" => MemProgData <= X"11090001";
			when X"00000034" => MemProgData <= X"00005020";
			when X"00000038" => MemProgData <= X"214b0002";
			when X"0000003C" => MemProgData <= X"00016020";
			when X"00000040" => MemProgData <= X"ac0c200c";
			when X"00000044" => MemProgData <= X"8c0d200c";
			when X"00000048" => MemProgData <= X"00437026";
			when X"0000004C" => MemProgData <= X"01c07024";
			when X"00000050" => MemProgData <= X"00227825";
			when X"00000054" => MemProgData <= X"08000015";		
			when others => MemProgData <= X"00000000"; -- Rest of empty memory
		end case;
	end process EscrituraMemProg;

end Simple;

