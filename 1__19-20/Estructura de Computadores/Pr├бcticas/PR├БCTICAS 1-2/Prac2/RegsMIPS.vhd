----------------------------------------------------------------------
-- File: RegsMIPS.vhd
-- Description: Register bank of the microprocessor MIPS
-- Date last modification: 2019-02-22

-- Authors: 
-- Sbuject: Sbuject: C.E. 1st grade
-- Laboratory group:
-- Theory group:
-- Task: 2
-- Exercise: 1
----------------------------------------------------------------------


library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;

entity RegsMIPS is
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
end RegsMIPS;

architecture Practica of RegsMIPS is

	-- Type to store the registers
	type regs_t is array (0 to 31) of signed(31 downto 0);

	-- This signal contains the registers. The access is as follow:
	-- regs(i) is the access to the register i, where i is an integer.
	-- To convert from unsigened to integer it is necessary:
	-- to_integer(us), where us is an unsigned element.

	signal regs : regs_t;

begin  -- TASK
	
	regs(0)<=(others =>'0');
	
	------------------------------------------------------
	-- Writing of the register Wd
	------------------------------------------------------
	-- It is necessary to write the content of Wd3 in the register 
	-- pointed out by A3 when there is a rising edge of the clk 
	-- and the enable signal of writing We3 is active.
	-- The reset is asynchrnous. If the reset is active, all the registers
	-- are initialized to 0.
	
	writing: process(all)
	begin
		if NRst='0' then
		  for i in 31 downto 0 loop 
			 regs(i)<=(others =>'0');
		  end loop;
		  
		elsif rising_edge(Clk) and We3='1' and A3/="00000" then 
			regs(to_integer(A3))<=Wd3;
		end if;
		
		
	end process;

	------------------------------------------------------
	-- Reading of the register Rd1
	------------------------------------------------------
	-- It is necessary to read in Rd1 the register pointed out by A1. 
	-- The reading of R0 register, always is 0.
	
	Rd1<= regs(to_integer(A1)) ;
	
	------------------------------------------------------
	-- Reading of the register Rd2
	------------------------------------------------------
	-- It is necessary to read in Rd2 the register pointed out by A2. 
	-- The reading of R0 register, alwyas is 0.
	
	Rd2<= regs(to_integer(A2));
	
	

end Practica;


