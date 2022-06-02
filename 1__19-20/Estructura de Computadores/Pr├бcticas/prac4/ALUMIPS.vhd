----------------------------------------------------------------------
-- File: ALUMIPS.vhd
-- Description: ALU of the microprocessor MIPS
-- Date last modification: 

-- Authors: 
-- Sbuject: Sbuject: C.E. 1st grade
-- Laboratory group:
-- Theory group:
-- Task: 2
-- Exercise: 2
----------------------------------------------------------------------


library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;


entity ALUMIPS is
	port(
  		Op1 : in signed(31 downto 0);
		Op2 : in signed(31 downto 0);
		ALUControl : in std_logic_vector (2 downto 0);
		Res : out signed(31 downto 0);
		Z : out std_logic
		);
end ALUMIPS;

architecture Practica of ALUMIPS is
  
  signal slt: signed(31 downto 0);
  
begin
  
  z<='1' when res=x"00000000" else 
      '0';
  
  slt<= x"00000001" when OP1<OP2 else 
        x"00000000";
  
  with ALUcontrol select
    Res<= (OP1 AND OP2) when "000",
          (OP1 OR OP2) when "001", 
          (OP1 + OP2) when "010", 
          (OP1 XOR OP2) when "011", 
          (OP1 NOR OP2) when "101", 
          (OP1 - OP2) when "110", 	
  	        slt when others;

end Practica;
