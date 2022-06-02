----------------------------------------------------------------------
-- File: MicroMIPS.vhd
-- Description: microprocessor MIPS
-- Date last modification: 2020-04-04

-- Authors: Pablo Cuesta
-- Sbuject: Sbuject: C.E. 1st grade
-- Laboratory group: 2151
-- Theory group: 215
-- Task: 4
-- Exercise: 3
----------------------------------------------------------------------


library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;

entity MicroMIPS is
	port (
		Clk : in std_logic; -- Reloj
		NRst : in std_logic; -- Low level active Reset 
		MemProgAddr : out unsigned(31 downto 0); -- Address for the program memory
		MemProgData : in unsigned(31 downto 0); -- Operation code
		MemDataAddr : out unsigned(31 downto 0); -- Address for the data memory
		MemDataDataRead : in signed(31 downto 0); -- Data to read in the data memory
		MemDataDataWrite : out signed(31 downto 0); -- Data to save in the data memory
		MemDataWE : out std_logic
	);
end MicroMIPS;
 
architecture behavior OF MicroMIPS is

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
	
	--Declaration of UnidadControl
	component UnidadControl
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
		RegDest : out  std_logic
		);
	end component;

	-- auxiliary signals
		signal Wd3: signed(31 downto 0);
		signal Rd2: signed(31 downto 0);
		signal Op1: signed(31 downto 0);
		signal Op2: signed(31 downto 0);
		signal Z: std_logic;
		signal Branch: std_logic;
		signal Jump: std_logic;
		signal MemToReg: std_logic;
		signal ALUSrc: std_logic;
		signal ALUControl: std_logic_vector(2 downto 0);
		signal ExtCero: std_logic;
		signal RegWrite: std_logic;
		signal RegDest: std_logic;
		signal A3: unsigned(4 downto 0);
		signal PCSrc: std_logic;
		signal SignImm: signed(31 downto 0);
		signal ZeroImm: signed(31 downto 0);
		signal resultALU: signed(31 downto 0);
		signal BTA: unsigned(31 downto 0);
		signal JTA: unsigned(31 downto 0);
		signal PCnext: unsigned(31 downto 0);
		
begin
	-- Instantiation of RegsMIPS
	regi: RegsMIPS port map(
		Clk => Clk,
		NRst => NRst,
		A1 => (MemProgData(25 downto 21)),
		Rd1 => Op1,
		A2 => (MemProgData(20 downto 16)),
		Rd2 => Rd2,
		A3 => A3,
		Wd3 => Wd3,
		We3 => RegWrite
		);
	
	-- Instantiation of ALUMIPS
	alum: ALUMIPS port map(
		Op1 => Op1,
		Op2 => Op2,
		ALUControl => ALUControl,
		Res => resultALU,
		Z => Z
		);
	
		
	--Instantiation of UnidadControl
	uctrl: UnidadControl port map(
		OPCode => std_logic_vector(MemProgData(31 downto 26)),
		Funct => std_logic_vector(MemProgData(5 downto 0)),
		Jump => Jump,
		Branch => Branch,
		MemToReg => MemToReg,
		MemWrite => MemDataWE,
		ALUSrc => ALUSrc,
		ALUControl => ALUControl,
		ExtCero => ExtCero,
		RegWrite => RegWrite,
		RegDest => RegDest
		);
		
	
	
	PCSrc <= Z AND Branch;
	
	SignImm <= signed((31 downto 16 => MemProgData(15)) & MemProgData(15 downto 0));
	
	ZeroImm <= signed((31 downto 16 => '0') & MemProgData(15 downto 0));
	
	PCnext <= MemProgAddr + 4;
	BTA <= (unsigned(SignImm) sll 2) + (PCnext);
	JTA <= PCnext (31 downto 28) & MemProgData(25 downto 0) & "00";
	
		
	Op2 <= Rd2 when ALUSrc = '0' else
			ZeroImm when ExtCero = '1' else
			SignImm;
			
	A3 <= MemProgData(20 downto 16) when RegDest = '0' else
		  MemProgData(15 downto 11);
	
	with MemToReg select Wd3 <=
		MemDataDataRead when '1',
		resultALU when others;
	
	--outputs
	
	MemDataAddr <= unsigned(resultALU);
	MemDataDataWrite <= Rd2;
	
	ProgramCounter: process(all)
		begin
			if NRst = '0' then 
				MemProgAddr <= (others => '0');
			elsif rising_edge(Clk) then 
				if Jump = '1' then
					MemProgAddr <= JTA;
				elsif PCSrc = '1' then 
					MemProgAddr <= BTA;
				else 
					MemProgAddr <= PCnext;
				end if;
			end if;		
	end process;


end behavior;
