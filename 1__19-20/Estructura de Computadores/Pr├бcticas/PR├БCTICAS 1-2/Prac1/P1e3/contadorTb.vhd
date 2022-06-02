

library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;

entity ContadorTb is
end contadorTb;

architecture BEHAV of Contadortb is
  
	component Contador
		port( Clk,Reset,Ce,Up: in std_logic;
			  Q: out unsigned (7 downto 0));
	end component;

	signal Clk: std_logic;
	signal Reset: std_logic;
	signal Ce: std_logic;
	signal Up: std_logic;

	signal Q: unsigned (7 downto 0);

	constant DELAY: time:=10 ns;

	begin
		
		uut: contador port map(
		  Clk=>Clk,
		  Ce=>Ce,
		  Up=>Up,
		  Reset=>Reset,
		Q=>Q);
		
		
	  CLOCKPROCESS: process
		begin 
		  Clk<='0';
		  wait for DELAY/2;
		  Clk<='1';
		  wait for DELAY/2;
	  end process;
	  
	  SIMULATIONPROCESS: process
		begin
		  Up <= '1';
		  Ce <='0';
		  Reset <= '1';
		  
		wait for DELAY;
		assert q=x"00" report "Reset error" severity failure;
		
		wait for DELAY;
		assert q=x"00" report "Ce error" severity failure;
		
		reset<='0';
		Ce<='1';
	   
		for i in 0 to 270 loop
		  assert Q= unsigned(to_unsigned(i mod 256, 8)) 
			report "Error i= " & to_string(i)
			severity failure;
			if i = 200 then report "Hola";
			end if;
		  wait for DELAY;
		end loop;
	  
	  
		  Up <= '0';
		  Ce <='0';
		  Reset <= '1';
		  
		wait for DELAY;
		assert q=x"00" report "reset error" severity failure;
		
		reset<='0';
		Ce<='1';
	   
		for i in 0 to 270 loop
		  assert Q= unsigned(to_unsigned((256-i mod 256), 8)) 
			report "Error i= " & to_string(i)
			severity failure;
		  wait for DELAY;
		end loop;

		report "Simulation correct";
		wait;
    end process;

end BEHAV;
  
        
      
  
    
    
    

