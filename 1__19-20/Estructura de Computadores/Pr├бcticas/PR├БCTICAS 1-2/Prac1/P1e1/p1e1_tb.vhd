library IEEE;
use IEEE.std_logic_1164.ALL;
use IEEE.numeric_std.ALL;

entity P1e1_tb is
end P1e1_tb;

architecture practica of P1e1_tb is
  
  component P1e1
    port(
		  A, B, C: in std_logic;
		  Z: out std_logic
		);
	end component;
	
	signal sA: std_logic :='0';
	signal sB: std_logic :='0';
	signal sC: std_logic :='0';
	
	signal sz: std_logic;
	
	constant DELAY: time := 10 ns;
	
	begin 
	  
	  uut: P1e1 port map(
	    A => sA,
	    B => sB,
	    C => sC,
	    Z => sz	      
	  );
	  
	  process
	    begin
	      sA<='0';sB<='0';sC<='0';
	      wait for DELAY;
	      assert sz='0'
	         report "Error case 000"
	         severity failure;
	      
	      sA<='0';sB<='0';sC<='1';
	      wait for DELAY;
	      assert sz='0'
	         report "Error case 000"
	         severity failure;
	         
	      sA<='0';sB<='1';sC<='0';
	      wait for DELAY;
	      assert sz='0'
	         report "Error case 000"
	         severity failure;
	         
	      sA<='0';sB<='1';sC<='1';
	      wait for DELAY;
	      assert sz='1'
	         report "Error case 000"
	         severity failure;
	         
	      sA<='1';sB<='0';sC<='0';
	      wait for DELAY;
	      assert sz='0'
	         report "Error case 000"
	         severity failure;
	         
	      sA<='1';sB<='0';sC<='1';
	      wait for DELAY;
	      assert sz='1'
	         report "Error case 000"
	         severity failure;
	         
	      sA<='1';sB<='1';sC<='0';
	      wait for DELAY;
	      assert sz='0'
	         report "Error case 000"
	         severity failure;
	         
	      sA<='1';sB<='1';sC<='1';
	      wait for DELAY;
	      assert sz='1'
	         report "Error case 000"
	         severity failure;
	         
	      report "Simulation succes";
	      wait;
	    end process;
	    
end practica;