----------------------------------------------------------------------
-- File: ALUMIPSTb.vhd
-- Description: Testbench for ALU of the microprocessor MIPS
-- Date last modification: 2019-02-22

-- Authors: Sof�a Mart�nez (2019,2020),
-- Alberto S�nchez (2010, 2012, 2013, 2016, 2017),
-- Fernando L�pez Colino (2010),
-- �ngel de Castro (2009, 2014, 2015),
-- Sergio L�pez-Buedo (2005), Ju�n Gonz�lez (2005)
-- Sbuject: Sbuject: C.E. 1st grade
-- Laboratory group:
-- Theory group:
-- Task: 2
-- Exercise: 2
----------------------------------------------------------------------


library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;

entity ALUMIPSTb is
end ALUMIPSTb;

architecture Practica of ALUMIPSTb is

	component ALUMIPS
	port(
   		Op1 : in signed (31 downto 0);
		Op2 : in signed (31 downto 0);
		ALUControl : in std_logic_vector (2 downto 0);
		Res : out signed(31 downto 0);
		Z : out std_logic

	);
	end component;

	signal op1 : signed(31 downto 0);
	signal op2 : signed(31 downto 0);
	signal ALUControl : std_logic_vector(2 downto 0);
	signal res : signed(31 downto 0);
	signal z : std_logic;

	--
	-- types and constants needed to save the arrays of test cases 
	-- data structure

	-----------------------------------------------------------------------------
	-- To do the tests , we define a serie of test cases. It is a register, which
	-- cointains operators and results, which should be obtained to differents 
	-- calculations and their flags	
	-----------------------------------------------------------------------------

	type CASOSPRUEBAt is record
		op1 : signed(31 downto 0);
		op2 : signed(31 downto 0);
		flagsAdd : std_logic_vector(3 downto 0);
		resAdd : signed(31 downto 0);
		flagsAnd : std_logic_vector(3 downto 0);
		resAnd : signed(31 downto 0);
		flagsOr : std_logic_vector(3 downto 0);
		resOr : signed(31 downto 0);
		flagsXor : std_logic_vector(3 downto 0); 
		resXor :  signed(31 downto 0);
		flagsSub : std_logic_vector(3 downto 0);
		resSub: signed(31 downto 0);
		flagsNor : std_logic_vector(3 downto 0);
										 
										 
													  
										  
															  
										  
		resNor : signed(31 downto 0);
										  
		flagsNand : std_logic_vector (3 downto 0);
		resNand : signed(31 downto 0);
		flagsSlt : std_logic_vector(3 downto 0);
										 
		resSlt : signed(31 downto 0);
	end record;

	-- Be careful with the shift! In ARC Op1 is shifted and in MIPS Op2 is shifted. 
	-- This test-bench checks the shift left with MIPS crierial, but the other with ARC
	
	-- Number of test cases 
	constant NUMCASOSPRUEBA : integer := 20;

  -- array of test cases
  type CASOSPRUEBAa is array (1 to NUMCASOSPRUEBA) of CASOSPRUEBAt;
  
  -- The order in data is as follow:
  -- FLAGS: NZCV          
  -- CALCULATIONS: add and or nor srl
	constant casosPrueba : CASOSPRUEBAa :=
	(
		-- Op1         Op2        F_Add   Res_Add    F_And   Res_And    F_Or   Res_Or      F_Xor   Res_Xor    F_Sub   Res_Sub    F_Nor   Res_Nor     F_Nand   Res_Nand    F_Slt  Res_Slt   
	-- 1
		(x"00000000",x"00000000","0100",x"00000000","0100",x"00000000","0100",x"00000000","0100",x"00000000","0100",x"00000000","1000",x"FFFFFFFF", "1000", x"FFFFFFFF", "0100", x"00000000"),
	-- 2
		(x"FFFFFFFF",x"FFFFFFFF","1010",x"FFFFFFFE","1000",x"FFFFFFFF","1000",x"FFFFFFFF","0100",x"00000000","0100",x"00000000","0100",x"00000000", "0100", x"00000000", "0100", x"00000000"),
	-- 3
		(x"FFFFFFFF",x"00000000","1000",x"FFFFFFFF","0100",x"00000000","1000",x"FFFFFFFF","1000",x"FFFFFFFF","1000",x"FFFFFFFF","0100",x"00000000", "1000", x"FFFFFFFF", "0000", x"00000001"),
	-- 4
		(x"00000000",x"FFFFFFFF","1000",x"FFFFFFFF","0100",x"00000000","1000",x"FFFFFFFF","1000",x"FFFFFFFF","0010",x"00000001","0100",x"00000000", "1000", x"FFFFFFFF", "0100", x"00000000"),
	-- 5
		(x"55555555",x"AAAAAAAA","1000",x"FFFFFFFF","0100",x"00000000","1000",x"FFFFFFFF","1000",x"FFFFFFFF","1011",x"AAAAAAAB","0100",x"00000000", "1000", x"FFFFFFFF", "0100", x"00000000"),
	-- 6
		(x"AAAAAAAA",x"55555555","1000",x"FFFFFFFF","0100",x"00000000","1000",x"FFFFFFFF","1000",x"FFFFFFFF","0001",x"55555555","0100",x"00000000", "1000", x"FFFFFFFF", "0000", x"00000001"),
	--7
		(x"FFFF0000",x"0000FFFF","1000",x"FFFFFFFF","0100",x"00000000","1000",x"FFFFFFFF","1000",x"FFFFFFFF","1000",x"FFFE0001","0100",x"00000000", "1000", x"FFFFFFFF", "0000", x"00000001"),
	-- 8
		(x"0000FFFF",x"FFFF0000","1000",x"FFFFFFFF","0100",x"00000000","1000",x"FFFFFFFF","1000",x"FFFFFFFF","0010",x"0001FFFF","0100",x"00000000", "1000", x"FFFFFFFF", "0100", x"00000000"),
	-- 9
		(x"0000FFFF",x"0000FFFF","0000",x"0001FFFE","0000",x"0000FFFF","0000",x"0000FFFF","0100",x"00000000","0100",x"00000000","1000",x"FFFF0000", "1000", x"FFFF0000", "0100", x"00000000"),
	-- 10
		(x"FFFF0000",x"FFFF0000","1010",x"FFFE0000","1000",x"FFFF0000","1000",x"FFFF0000","0100",x"00000000","0100",x"00000000","0000",x"0000FFFF", "0000", x"0000FFFF", "0100", x"00000000"),
	-- Some tests for overflows and carries-- 
	-- 11
		(x"7FFFFFFF",x"00000001","1001",x"80000000","0000",x"00000001","0000",x"7FFFFFFF","0000",x"7FFFFFFE","0000",x"7FFFFFFE","1000",x"80000000", "1000", x"FFFFFFFE", "0100", x"00000000"),
	-- 12
		(x"00000001",x"7FFFFFFF","1001",x"80000000","0000",x"00000001","0000",x"7FFFFFFF","0000",x"7FFFFFFE","1010",x"80000002","1000",x"80000000", "1000", x"FFFFFFFE", "0000", x"00000001"),
	-- 13
		(x"80000000",x"FFFFFFFF","0011",x"7FFFFFFF","1000",x"80000000","1000",x"FFFFFFFF","0000",x"7FFFFFFF","1010",x"80000001","0100",x"00000000", "0000", x"7FFFFFFF", "0000", x"00000001"),
	-- 14
		(x"FFFFFFFF",x"80000000","0011",x"7FFFFFFF","1000",x"80000000","1000",x"FFFFFFFF","0000",x"7FFFFFFF","0000",x"7FFFFFFF","0100",x"00000000", "0000", x"7FFFFFFF", "0100", x"00000000"),
	-- 15
		(x"65F3B6AE",x"48F6DAC6","1001",x"AEEA9174","0000",x"40F29286","0000",x"6DF7FEEE","0000",x"2D056C68","0000",x"1CFCDBE8","1000",x"92080111", "1000", x"BF0D6D79", "0100", x"00000000"),
	-- 16
		(x"A8D93BA0",x"D575AF41","0011",x"7E4EEAE1","1000",x"80512B00","1000",x"FDFDBFE1","0000",x"7DAC94E1","1010",x"D3638C5F","0000",x"0202401E", "0000", x"7FAED4FF", "0000", x"00000001"),
	-- 17
		(x"3A361FBD",x"27F5AA09","0000",x"622BC9C6","0000",x"22340A09","0000",x"3FF7BFBD","0000",x"1DC3B5B4","0000",x"124075B4","1000",x"C0084042", "1000", x"DDCBF5F6", "0100", x"00000000"),
	-- 18
		(x"E519AC0D",x"F27D71B6","1010",x"D7971DC3","1000",x"E0192004","1000",x"F77DFDBF","0000",x"1764DDBB","1010",x"F29C3A57","0000",x"08820240", "0000", x"1FE6DFFB", "0000", x"00000001"),
	-- To finish, a couple of testes of shifts--
	-- 19
		(x"12345678",x"00000010","0000",x"12345688","0000",x"00000010","0000",x"12345678","0000",x"12345668","0000",x"12345668","1000",x"EDCBA987", "1000", x"FFFFFFEF", "0100", x"00000000"),
	-- 20
		(x"FEDCBA98",x"00000008","1000",x"FEDCBAA0","0000",x"00000008","1000",x"FEDCBA98","1000",x"FEDCBA90","1000",x"FEDCBA90","0000",x"01234567", "1000", x"FFFFFFF7", "0000", x"00000001")
	);
  
  
	-- Time which we will wait until ALU answers. 
	constant TDELAY : time := 25 ns;

	-- We add working flags  for every individual calculation
								 
	signal andFlags : boolean := true;
	signal andRes : boolean := true;
	signal orFlags : boolean := true;
	signal orRes : boolean := true;
	signal addFlags : boolean := true;
	signal addRes : boolean := true;
	signal xorFlags : boolean := true;
	signal xorRes : boolean := true;
--	signal nandFlags : boolean := true;
--	signal nandRes : boolean := true;
	signal norFlags : boolean := true;
	signal norRes : boolean := true;
	signal subFlags : boolean := true;
	signal subRes : boolean := true;
	signal sltRes : boolean := true;
	signal sltFlags : boolean := true;


begin

	-- Instantiation of ALU
	UUT : ALUMIPS
	port map (
		OP1 => OP1,
		OP2 => OP2,
		RES => RES,
		ALUControl => ALUControl,
		Z => z
	);


  -----------------------------------------------------------------------------
  -- Main process of test
  -----------------------------------------------------------------------------
	Test : process
		--variable casosOrBien : integer := 0;
		variable casosAndBien : integer := 0;
	begin

		-- We repeat for all the test cases
		for i in 1 to NUMCASOSPRUEBA loop

			-- We introduce the operands
			OP1 <= casosPrueba(i).op1;
			OP2 <= casosPrueba(i).op2;

			-------------------------------------------------------------------------
			-- Test for the AND operation
			-------------------------------------------------------------------------
			ALUControl <= "000";

			-- Waiting for the answer
			wait for TDELAY;

			-- Checking the flags 
			assert casosPrueba(i).flagsAnd(2) = Z 
				report "Error in the flags for the AND operation in the case " & to_string(i) 
					severity note;

			if (casosPrueba(i).flagsAnd(2)/=Z ) then
				andFlags <= false;
			end if;

			-- Checking the result 
			assert casosPrueba(i).resAnd = res 
				report "Error in the result for the AND operation in the case " & to_string(i)
					severity note;
			
			if (casosPrueba(i).resAnd/=res) then
				andRes <= false;
			end if;

			-------------------------------------------------------------------------
			-- Test for the OR operation
			-------------------------------------------------------------------------
			ALUControl <= "001";

			-- Waiting for the answer
			wait for TDELAY;

						 
			assert casosPrueba(i).flagsOr(2)=Z 
				report "Error in the flags for the OR operation in the case " & to_string(i)
					severity note;
			
			if (casosPrueba(i).flagsOr(2)/=Z ) then
				OrFlags <= false;
			end if;

			-- Checking the result
			assert casosPrueba(i).resOr=res 
				report "Error in the result for the OR operation in the case " & to_string(i)
					severity note;  
			
			if (casosPrueba(i).resor/=res) then
				orRes <= false;
			end if;

			------------------------------------------------------------------------
			-- Test for the ADDITION
			------------------------------------------------------------------------
			ALUControl <= "010";

			-- Waiting for the answer
			wait for TDELAY;

			-- Checking the flags 
			assert casosPrueba(i).flagsAdd(2)=Z 
				report "Error in the flags for the addition in the case " & to_string(i) 		
					severity note;

			if (casosPrueba(i).flagsAdd(2)/=Z ) then
				addFlags <= false;
			end if;

			-- Checking the results 
			assert casosPrueba(i).resAdd=res 
				report "Error in the result for the addition in the case " & to_string(i) 
					severity note;

			if (casosPrueba(i).resAdd/=res) then
				addRes <= false;
			end if;

			-------------------------------------------------------------------------
			-- Test for the XOR
			-------------------------------------------------------------------------

			ALUControl <= "011";

			-- Waiting for the answer
			wait for TDELAY;

			-- Checking the flags 
			assert casosPrueba(i).flagsxOr(2)=Z 
				report "Error in the flags in the XOR case" & to_string(i)
					severity note;
			
			if (casosPrueba(i).flagsxOr(2)/=Z ) then
				xorFlags <= false;
			end if;

			-- Comprobar el resultado
			assert casosPrueba(i).resxOr=res 
				report "Error in the result in the XOR case " & to_string(i)
					severity note;
			
			if (casosPrueba(i).resOr/=res) then
				orRes <= false;
			end if;

			-- -------------------------------------------------------------------------
			-- -- Prueba de la operacion NAND 
			-- -------------------------------------------------------------------------
			-- ALUControl <= "100";

			-- -- Waiting for the answer
			-- wait for TDELAY;

			-- -- Checking the flags
			-- assert casosPrueba(i).flagsAnd(2)=Z 
				-- report "Error in the flags in the NAND case " & to_string(i) 
					-- severity note;

			-- if (casosPrueba(i).flagsAnd(2)/=Z ) then
				-- andFlags <= false;
			-- end if;

			-- -- Comprobar el resultado
			-- assert casosPrueba(i).resAnd = res 
				-- report "Error in the result in the NAND case " & to_string(i)
					-- severity note;
			
			-- if (casosPrueba(i).resAnd/=res) then
				-- andRes <= false;
			-- end if;
		 
			-------------------------------------------------------------------------
			-- Test for the NOR operation
			-------------------------------------------------------------------------
			ALUControl <= "101";

			-- Waiting for the answer
			wait for TDELAY;

			assert casosPrueba(i).flagsNor(2)=Z 
				report "FError in the flags for the nor operation in the case " & to_string(i)
					severity note;
			
			if (casosPrueba(i).flagsNor(2)/=Z ) then
				norFlags <= false;
			end if;

			-- Checking the flags
			assert casosPrueba(i).resNor=res 
				report "rror in the result for the nor operation in the case " & to_string(i)
					severity note;  
			
			if (casosPrueba(i).resNor/=res) then
				norRes <= false;
			end if;


			-------------------------------------------------------------------------
			-- Test for the SUBTRACTION 
			-------------------------------------------------------------------------
			ALUControl <="110";
			-- Waiting for the answer
			wait for TDELAY; 
			
			--  Checking the flags 
			assert casosPrueba(i).flagsSub(2)=Z 
				report "Error in the flags for the subtraction in the case " & to_string(i) 		
					severity note;
			
			if (casosPrueba(i).flagsSub(2)/=Z ) then
				subFlags <= false;
			end if;
			
			-- Checking the result 
			assert casosPrueba(i).resSub=res 
				report "Error in the result for the subtraction in the case " & to_string(i) 
					severity note;
			
			if (casosPrueba(i).resSub/=res) then
				subRes <= false;
			end if;

			-------------------------------------------------------------------------
			-- Test for the SLT operation
			-------------------------------------------------------------------------
			ALUControl <= "111";

			-- Waiting for the answer
			wait for TDELAY;

			assert casosPrueba(i).flagsSlt(2) = Z 
				report "Error in the flags for the slt operation in the case " & to_string(i)
					severity note;
			
			if (casosPrueba(i).flagsSlt(2)/=Z ) then
				sltFlags <= false;
			end if;

			-- Checking the flags
			assert casosPrueba(i).resSlt=res 
				report "Error in the result for the slt operation in the case " & to_string(i) 
					severity note;  
			
			if (casosPrueba(i).resSlt/=res) then
				sltRes <= false;
			end if;
			
		end loop;
	

		-- In this moment, the simulaion has finished
		report "FINISHED SIMULATION! Check if there are errors before this message" severity note;
		
		wait;
	end process test;  

end PRACTICA;
      