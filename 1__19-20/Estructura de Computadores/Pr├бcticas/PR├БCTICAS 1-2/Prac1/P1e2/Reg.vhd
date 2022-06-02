library IEEE;
use IEEE.std_logic_1164.ALL;
use IEEE.numeric_std.ALL;

entity Reg is
    port ( 
		D2 : in  std_logic_vector (7 downto 0);
		Reset : in  std_logic;
		Clk : in  std_logic;
		Ce : in  std_logic;
		Q2 : out  std_logic_vector (7 downto 0)
	);
end Reg;

architecture behav of Reg is
begin 
	process (all)
	begin	
		if Reset = '1' then
			Q2 <= (others => '0');
		elsif rising_edge (Clk) then 
			if Ce ='1' then
				Q2 <= D2;
			end if;
		end if;
	end process;
end behav;