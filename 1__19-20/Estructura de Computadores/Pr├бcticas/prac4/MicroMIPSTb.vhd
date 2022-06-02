----------------------------------------------------------------------
-- Subject: Computer Structure. GII and DGIIM grade. 1st level
-- File: MicroMIPSTb.vhd
-- Description: Testbench for the microprocessor MIPS
-- This file helps to the first validation in the exercise 3
----------------------------------------------------------------------


library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;

entity MicroMIPSTb is
end MicroMIPSTb;
 
architecture Test OF MicroMIPSTb is
 
  -- Declaration of the micro (without memory)
	component MicroMIPS
	port (
		Clk : in std_logic; -- Reloj
		NRst : in std_logic; -- Low level active Reset 
		MemProgAddr :out unsigned(31 downto 0); -- Address for the program memory
		MemProgData : in unsigned(31 downto 0); -- Operation code
		MemDataAddr : out unsigned(31 downto 0); -- Address for the data memory
		MemDataDataRead : in signed(31 downto 0);  -- Data to read in the data memory
		MemDataDataWrite : out signed(31 downto 0);  -- Data to save in the data memory
		MemDataWE : out std_logic
	);
	end component;

	-- Declaration of the code/program memory
	component MemProgMIPS
	port (
		MemProgAddr : in unsigned(31 downto 0); -- Address for the program memory
		MemProgData : out unsigned(31 downto 0)  -- Opeartion code
	);
	end component;
	
	-- Declaration of the data memory
	component MemDataMIPS
	port (
		Clk : in std_logic;
		NRst : in std_logic;
		MemDataAddr : in unsigned(31 downto 0); -- Address for the data memory	
		MemDataDataWrite : in signed(31 downto 0);-- Data to write from the data memory
		MemDataWE : in std_logic; -- Enable of writting in data memory
		MemDataDataRead : out signed(31 downto 0) -- Data to read from the data memory
	);
	end component;

	-- Inputs to the micro
	-- In the testbenches could be used intial values in the declarations
	-- but in the modules is not possible due to are not synthesizable 
	signal memProgData : unsigned(31 downto 0) := (others => '0');
	signal memDataDataRead :signed(31 downto 0) := (others => '0');
	signal nRst : std_logic := '0';
	signal clk : std_logic := '0';

	-- Outpus of the micro
	signal memProgAddr, memDataAddr : unsigned(31 downto 0);
	signal memDataDataWrite : signed(31 downto 0) := (others => '0');
	signal memDataWE : std_logic := '0';

	-- Clock period
	constant CLKPERIOD : time := 10 ns;

	-- End of simulation, in case we want to kill the simulation if there are no events
	signal finSimu : boolean := false;

begin
 
	-- Instantiation of micro
	uut: MicroMIPS
	port map(
		Clk => Clk,
		NRst => NRst,
		MemProgAddr => memProgAddr,
		MemProgData => memProgData,
		MemDataAddr => memDataAddr,
		MemDataDataRead => memDataDataRead,
		MemDataDataWrite => memDataDataWrite,
		MemDataWE => memDataWE
	);

	-- Intantiation of code/program memory
	mprog: MemProgMIPS
	port map (
		MemProgAddr => memProgAddr,
		MemProgData => memProgData
	);

	-- Instantiation of data memory
	mdata: MemDataMIPS
	port map (
		Clk => Clk,
		NRst => NRst,
		MemDataAddr => memDataAddr,
		MemDataDataRead => memDataDataRead,
		MemDataDataWrite => memDataDataWrite,
		MemDataWE => memDataWE
		
	);
	
	CLKPROCESS: process
	begin
	while (not finSimu) loop
		clk <= '0';
		wait for CLKPERIOD/2;
		clk <= '1';
		wait for CLKPERIOD/2;
	end loop;
	wait;
	end process;

	-- Main process to active the signals.
	-- Only it is necessary to active the reset. The rest of the testbench
	-- is changed with the initial value of memories, which
	-- build the program to be executed.
	StimProc: process
	begin
		nRST <= '0'; -- Reset starts active
		wait for CLKPERIOD*2;
		nRST <= '1'; -- Reset disarmed and start the execution
		wait for CLKPERIOD*1000;
		finSimu <= true; -- If the simulation has more than 100 instructions
		-- it would be necessary to wait more time before killing the simulation.
		wait; -- Nothing else is done with the reset
	end process;

end Test;
