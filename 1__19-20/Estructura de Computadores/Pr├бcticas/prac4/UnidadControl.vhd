----------------------------------------------------------------------
-- File: UnidadControl.vhd
-- Description: control unit for the microprocessor MIPS
-- Date last modification: 2020-04-04

-- Authors: Pablo Cuesta
-- Sbuject: Sbuject: C.E. 1st grade
-- Laboratory group: 2151
-- Theory group: 215
-- Task: 4
-- Exercise: 2
----------------------------------------------------------------------

library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;
 
entity UnidadControl is
	port(
	OPCode : in  std_logic_vector (5 downto 0); -- OPCode of the instruction
	Funct : in std_logic_vector(5 downto 0); -- Funct of the instruction
	-- Signals for the PC
	Jump : out  std_logic;
	Branch : out  std_logic;
	-- Signals to the memory
	MemToReg : out  std_logic;
	MemWrite : out  std_logic;
		
	-- Sifnals for the ALU
	ALUSrc : out  std_logic;
	ALUControl : out  std_logic_vector (2 downto 0);
	ExtCero : out std_logic;
		
	-- Signals for the GPR
	RegWrite : out  std_logic;
	RegDest : out  std_logic);
end UnidadControl;
 
architecture behavior of UnidadControl is

	begin 
		UC: process(all)
		begin
			if OPCode = "000000" then 
			
				MemToReg <= '0';				
				MemWrite <= '0';				
				Branch <= '0';
				ALUSrc <= '0';
				RegDest <= '1';
				RegWrite <= '1';
				Jump <= '0';
				
				if Funct = "100100" then --and
					ALUControl <= "000";
					ExtCero <= '1';
				
				elsif Funct = "100000" then --add
					ALUControl <= "010";
					ExtCero <= '0';
				
				elsif Funct = "100010" then --sub
					ALUControl <= "110";
					ExtCero <= '0';
				
				elsif Funct = "100111" then --nor
					ALUControl <= "101";
					ExtCero <= '1';
					
				elsif Funct = "100110" then --xor
					ALUControl <= "011";
					ExtCero <= '1';
					
				elsif Funct = "100101" then --or
					ALUControl <= "001";
					ExtCero <= '1';
					
				else --Funct = "101010" then --slt
					ALUControl <= "111";
					ExtCero <= '0';
					
				end if;
			
			elsif OPCode = "000010" then --jump
				MemToReg <= '-';				
				MemWrite <= '0';				
				Branch <= '0';
				ALUControl <= "---";
				ALUSrc <= '-';
				RegDest <= '-';
				RegWrite <= '0';
				ExtCero <= '-';
				Jump <= '1';
			elsif OPCode = "000100" then --beq
				MemToReg <= '-';				
				MemWrite <= '0';				
				Branch <= '1';
				ALUControl <= "110";
				ALUSrc <= '0';
				RegDest <= '-';
				RegWrite <= '0';
				ExtCero <= '-';
				Jump <= '0';
			elsif OPCode = "001000" then --addi
				MemToReg <= '0';				
				MemWrite <= '0';				
				Branch <= '0';
				ALUControl <= "010";
				ALUSrc <= '1';
				RegDest <= '0';
				RegWrite <= '1';
				ExtCero <= '0';
				Jump <= '0';
			elsif OPCode = "001100" then --andi
				MemToReg <= '0';				
				MemWrite <= '0';				
				Branch <= '0';
				ALUControl <= "000";
				ALUSrc <= '1';
				RegDest <= '0';
				RegWrite <= '1';
				ExtCero <= '1';
				Jump <= '0';
			elsif OPCode = "001101" then --ori
				MemToReg <= '0';				
				MemWrite <= '0';				
				Branch <= '0';
				ALUControl <= "001";
				ALUSrc <= '1';
				RegDest <= '0';
				RegWrite <= '1';
				ExtCero <= '1';
				Jump <= '0';
			elsif OPCode = "100011" then --lw
				MemToReg <= '1';				
				MemWrite <= '0';				
				Branch <= '0';
				ALUControl <= "010";
				ALUSrc <= '1';
				RegDest <= '0';
				RegWrite <= '1';
				ExtCero <= '0';
				Jump <= '0';
			elsif OPCode = "101011" then --sw
				MemToReg <= '-';				
				MemWrite <= '1';				
				Branch <= '0';
				ALUControl <= "010";
				ALUSrc <= '1';
				RegDest <= '-';
				RegWrite <= '0';
				ExtCero <= '0';
				Jump <= '0';
			else-- OPCode = "001010" then --slti
				MemToReg <= '0';				
				MemWrite <= '0';				
				Branch <= '0';
				ALUControl <= "111";
				ALUSrc <= '1';
				RegDest <= '0';
				RegWrite <= '1';
				ExtCero <= '0';
				Jump <= '0';
			
			
			end if;
		end process;
	
end ;












