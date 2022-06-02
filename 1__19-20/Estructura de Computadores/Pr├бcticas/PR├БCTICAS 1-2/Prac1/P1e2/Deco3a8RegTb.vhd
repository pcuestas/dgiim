---------------------------------------------------------------------------------
-- File: Deco3a8RegTb.vhd
-- Description: Testbench for a 3 to 8 registered decoder
-- Last modification date: 2019-01-23

-- Authors: Sofia Martínez (2020), Alberto Sánchez (2012), Fernando López (2010) 
-- Subject: C.E. 1º grade
-- Laboratory group:
-- Theory group:
-- Task: 1
-- Exercise: 2
---------------------------------------------------------------------------------



library IEEE;
use IEEE.std_logic_1164.ALL;
use IEEE.numeric_std.ALL;

entity Deco3a8RegTb is
end Deco3a8RegTb;

architecture Simulacion of Deco3a8RegTb is

	-- Deco3a8Reg coponent definition

	component Deco3a8Reg
	port(
		D : IN  std_logic_vector(2 downto 0);
		Ce : IN  std_logic;
		Clk : IN  std_logic;
		Reset : IN  std_logic;
		Q : OUT  std_logic_vector(7 downto 0)
	);
	end component;


	-- Component inputs to be checked
	signal d : std_logic_vector(2 downto 0) := (others => '0');
	signal ce : std_logic := '0';
	signal clk : std_logic := '0';
	signal reset : std_logic := '0';

	-- Component outputs to be checked
	signal q : std_logic_vector(7 downto 0);

	-- Constants of testbench
	constant CLKPERIOD : time := 10 ns;
	constant ESPERA : time := 1 ns;
	constant NINPUT: integer := 3;

begin

	-- Deco3a8Reg component instantation
	uut: Deco3a8Reg port map (
		D => d,
		Ce => ce,
		Clk => clk,
		Reset => reset,
		Q => q
	);

	-- Clock generator process
	CLKPROCESS :process
	begin
		clk <= '0';
		wait for CLKPERIOD/2;
		clk <= '1';
		wait for CLKPERIOD/2;
	end process;

	-- Stimulation process
	stim_proc: process
	begin
		-- Initialization
		d <= "000";
		ce <= '0';
		reset <= '1';

		wait for ESPERA;
		Assert q = x"00" 
			report "Reset error"
			severity failure;

		-- Chip enable connected
		reset <= '0';
		ce <= '1';

		for i in 0 to 7 loop
			d <= std_logic_vector (to_unsigned(i,NINPUT));
			wait until clk = '1';
			wait for ESPERA;
			assert q = std_logic_vector (to_unsigned(2**i,8))
				report "Error with value i = "  & to_string(i)
				  severity failure;
		end loop;

		-- Chip enable disconnected
		ce <= '0';

		for i in 0 to 7 loop
			d <= std_logic_vector (to_unsigned(i,NINPUT));
			wait until clk = '1';
			wait for ESPERA;
			assert q = std_logic_vector (to_unsigned(2**7,8))
				report "Error with value i = "  & to_string(i)
				  severity failure;
		end loop;

		report "If this message appears, the simulation is correct";
		wait;
	end process;

end Simulacion;
