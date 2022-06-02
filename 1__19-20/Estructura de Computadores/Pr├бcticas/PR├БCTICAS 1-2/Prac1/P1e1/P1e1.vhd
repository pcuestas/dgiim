library IEEE;
use IEEE.std_logic_1164.ALL;
use IEEE.numeric_std.ALL;

entity P1e1 is 
	port(
		A, B, C: in std_logic;
		Z: out std_logic
		);
end P1e1;

architecture behav of P1e1 is 
	signal s: std_logic;
	
begin
	s<= A or B;
	Z<= C and s;

end behav;