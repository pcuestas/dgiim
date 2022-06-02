----------------------------------------------------------------------
-- File: Contador.vhd
-- Description: Contador de 8 bits con Chip Enable y selector de sentido de cuenta
-- Last modification date: 2020-01-29

-- Authors: Sofía Martínez(2020), Alberto Sánchez (2012), Fernando López (2010) 
-- Subject: E.C. 1º grado
-- Laboratory group:
-- Theory group:
-- Task: 1
-- Exercise: 3
----------------------------------------------------------------------

library IEEE;
use IEEE.std_logic_1164.ALL;
use IEEE.numeric_std.ALL;


-- 8 bits counter
entity Contador is
    Port (
		Clk : in  STD_LOGIC;
		Reset : in  STD_LOGIC;
		Ce : in  STD_LOGIC;
		Up : in  STD_LOGIC;
		Q : out  UNSIGNED (7 downto 0)
	);
end Contador;

architecture Practica of Contador is

	begin


	process (all)
	begin
		if Reset = '1' then
			Q <= (OTHERS => '0');
		elsif rising_edge(Clk) then
			if Ce = '1' then
				if Up = '1' then
					Q <= Q + 1;
				else
					Q <= Q - 1;
				end if;
			end if;
		end if;


	end process;

end Practica;
