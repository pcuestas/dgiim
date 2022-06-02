library IEEE;
use IEEE.std_logic_1164.ALL;
use IEEE.numeric_std.ALL;


entity Deco3a8Reg is
	port (
		D : in std_logic_vector (2 downto 0);
		Ce : in std_logic;
		Clk : in std_logic;
		Reset : in std_logic;
		Q : out std_logic_vector (7 downto 0)
	);
end Deco3a8Reg;

architecture behav of Deco3a8Reg is

	component Deco3a8 
		port(
			D1: in std_logic_vector (2 downto 0);
			Q1: out std_logic_vector (7 downto 0)
		);
	end component;


	component Reg is
		port ( 
			D2: in  std_logic_vector (7 downto 0);
			Reset: in  std_logic;
			Clk: in  std_logic;
			Ce: in  std_logic;
			Q2: out  std_logic_vector (7 downto 0)
		);
	end component;

	signal s1: std_logic_vector (7 downto 0);
	signal s2: std_logic_vector (7 downto 0);
	
begin

	dec: Deco3a8 port map(
		D1=> D,
		Q1=>s1
	);
	
	s2<=s1;
	
	regi: Reg port map(
		Q2=>Q,
		D2=>s2,
		Reset=>Reset,
		Clk=>Clk,
		Ce=>Ce
	);
	
	
	
end behav;
		