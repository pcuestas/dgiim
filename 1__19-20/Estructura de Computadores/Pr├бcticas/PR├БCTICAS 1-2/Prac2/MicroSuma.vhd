----------------------------------------------------------------------
-- File: MicroSuma.vhd
-- Description: Simplified Micro MIPS, only addition with immediate data
-- Date last modification: 2020-01-30
-- Authors: 
-- Sbuject: Sbuject: C.E. 1st grade
-- Laboratory group:
-- Theory group:
-- Task: 2
-- Exercise: 3
----------------------------------------------------------------------


library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;
							
entity MicroSuma is
	port (
		Clk : in std_logic; -- Clock
		NRst : in std_logic; -- Reset active low level
		MemProgAddr : out unsigned(31 downto 0); -- Address for the program memory
		MemProgData : in unsigned(31 downto 0) -- Operation code
	);
	end MicroSuma;

architecture Practica of MicroSuma is

	-- Declaration of RegsMIPS
	component RegsMIPS 
		port (
		Clk : in std_logic; -- Reloj
		NRst : in std_logic; -- Reset asíncrono a nivel bajo
		A1 : in unsigned(4 downto 0); -- Dirección para el puerto Rd1
		Rd1 : out signed(31 downto 0); -- Dato del puerto Rd1
		A2 : in unsigned(4 downto 0); -- Dirección para el puerto Rd2
		Rd2 : out signed(31 downto 0); -- Dato del puerto Rd2
		A3 : in unsigned(4 downto 0); -- Dirección para el puerto Wd3
		Wd3 : in signed(31 downto 0); -- Dato de entrada Wd3
		We3 : in std_logic -- Habilitación del banco de registros
	); 
		end component;
		
	-- Declaration of ALUMIPS
	
	component ALUMIPS 
		port(
  		Op1 : in signed(31 downto 0);
		Op2 : in signed(31 downto 0);
		ALUControl : in std_logic_vector (2 downto 0);
		Res : out signed(31 downto 0);
		Z : out std_logic
		);
		end component;
		
	
	-- Declaration of auxiliary signal
		signal result: signed(31 downto 0);
		signal Op1: signed(31 downto 0);
		signal Op2: signed(31 downto 0);
		
		
begin

	-- Instantiation of RegsMIPS
	regi: RegsMIPS port map(
		Clk => Clk,
		NRst => NRst,
		A1 => MemProgData(25 downto 21),
		Rd1 => Op1,
		A2 => (others => '0'),
		Rd2 => open,
		A3 => MemProgData(20 downto 16),
		Wd3 => result,
		We3 => '1'
		);
	
	-- Instantiation of ALUMIPS
	alum: ALUMIPS port map(
		Op1 => Op1,
		Op2 => Op2,
		ALUControl => "010",
		Res => result,
		Z => open
		);
	
	-- Sign Extension
	--Op2 (31 downto 16) <= (others => MemProgData(15));
	--Op2 (15 downto 0) <= MemProgData(15 downto 0);
	
	Op2 <= signed((31 downto 16 => MemProgData(15)) & MemProgData(15 downto 0));
	
	-- PC route
	pcroute: process(all)
		begin
			if NRst='0' then 
				MemProgAddr <= (others => '0');
			elsif rising_edge(Clk) then
				MemProgAddr <= MemProgAddr + x"00000004";
			end if;
		end process;

end Practica;

