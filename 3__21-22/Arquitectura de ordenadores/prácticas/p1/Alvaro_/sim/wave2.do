onerror {resume}
quietly WaveActivateNextPane {} 0
add wave -noupdate /processor_tb/i_processor/Clk
add wave -noupdate /processor_tb/i_processor/Reset
add wave -noupdate /processor_tb/i_processor/PC_reg
add wave -noupdate /processor_tb/i_processor/Instruction
add wave -noupdate /processor_tb/i_processor/RegsMIPS/Clk
add wave -noupdate /processor_tb/i_processor/RegsMIPS/Reset
add wave -noupdate /processor_tb/i_processor/RegsMIPS/A1
add wave -noupdate /processor_tb/i_processor/RegsMIPS/Rd1
add wave -noupdate /processor_tb/i_processor/RegsMIPS/A2
add wave -noupdate /processor_tb/i_processor/RegsMIPS/Rd2
add wave -noupdate /processor_tb/i_processor/RegsMIPS/A3
add wave -noupdate /processor_tb/i_processor/RegsMIPS/Wd3
add wave -noupdate /processor_tb/i_processor/RegsMIPS/We3
add wave -noupdate -radix decimal -childformat {{/processor_tb/i_processor/RegsMIPS/regs(0) -radix decimal} {/processor_tb/i_processor/RegsMIPS/regs(1) -radix decimal} {/processor_tb/i_processor/RegsMIPS/regs(2) -radix decimal} {/processor_tb/i_processor/RegsMIPS/regs(3) -radix decimal} {/processor_tb/i_processor/RegsMIPS/regs(4) -radix decimal} {/processor_tb/i_processor/RegsMIPS/regs(5) -radix decimal} {/processor_tb/i_processor/RegsMIPS/regs(6) -radix decimal} {/processor_tb/i_processor/RegsMIPS/regs(7) -radix decimal} {/processor_tb/i_processor/RegsMIPS/regs(8) -radix decimal} {/processor_tb/i_processor/RegsMIPS/regs(9) -radix decimal} {/processor_tb/i_processor/RegsMIPS/regs(10) -radix decimal} {/processor_tb/i_processor/RegsMIPS/regs(11) -radix decimal} {/processor_tb/i_processor/RegsMIPS/regs(12) -radix decimal} {/processor_tb/i_processor/RegsMIPS/regs(13) -radix decimal} {/processor_tb/i_processor/RegsMIPS/regs(14) -radix decimal} {/processor_tb/i_processor/RegsMIPS/regs(15) -radix decimal} {/processor_tb/i_processor/RegsMIPS/regs(16) -radix decimal} {/processor_tb/i_processor/RegsMIPS/regs(17) -radix decimal} {/processor_tb/i_processor/RegsMIPS/regs(18) -radix decimal} {/processor_tb/i_processor/RegsMIPS/regs(19) -radix decimal} {/processor_tb/i_processor/RegsMIPS/regs(20) -radix decimal} {/processor_tb/i_processor/RegsMIPS/regs(21) -radix decimal} {/processor_tb/i_processor/RegsMIPS/regs(22) -radix decimal} {/processor_tb/i_processor/RegsMIPS/regs(23) -radix decimal} {/processor_tb/i_processor/RegsMIPS/regs(24) -radix decimal} {/processor_tb/i_processor/RegsMIPS/regs(25) -radix decimal} {/processor_tb/i_processor/RegsMIPS/regs(26) -radix decimal} {/processor_tb/i_processor/RegsMIPS/regs(27) -radix decimal} {/processor_tb/i_processor/RegsMIPS/regs(28) -radix decimal} {/processor_tb/i_processor/RegsMIPS/regs(29) -radix decimal} {/processor_tb/i_processor/RegsMIPS/regs(30) -radix decimal} {/processor_tb/i_processor/RegsMIPS/regs(31) -radix decimal}} -expand -subitemconfig {/processor_tb/i_processor/RegsMIPS/regs(0) {-radix decimal} /processor_tb/i_processor/RegsMIPS/regs(1) {-radix decimal} /processor_tb/i_processor/RegsMIPS/regs(2) {-radix decimal} /processor_tb/i_processor/RegsMIPS/regs(3) {-radix decimal} /processor_tb/i_processor/RegsMIPS/regs(4) {-radix decimal} /processor_tb/i_processor/RegsMIPS/regs(5) {-radix decimal} /processor_tb/i_processor/RegsMIPS/regs(6) {-radix decimal} /processor_tb/i_processor/RegsMIPS/regs(7) {-radix decimal} /processor_tb/i_processor/RegsMIPS/regs(8) {-radix decimal} /processor_tb/i_processor/RegsMIPS/regs(9) {-radix decimal} /processor_tb/i_processor/RegsMIPS/regs(10) {-radix decimal} /processor_tb/i_processor/RegsMIPS/regs(11) {-radix decimal} /processor_tb/i_processor/RegsMIPS/regs(12) {-radix decimal} /processor_tb/i_processor/RegsMIPS/regs(13) {-radix decimal} /processor_tb/i_processor/RegsMIPS/regs(14) {-radix decimal} /processor_tb/i_processor/RegsMIPS/regs(15) {-radix decimal} /processor_tb/i_processor/RegsMIPS/regs(16) {-radix decimal} /processor_tb/i_processor/RegsMIPS/regs(17) {-radix decimal} /processor_tb/i_processor/RegsMIPS/regs(18) {-radix decimal} /processor_tb/i_processor/RegsMIPS/regs(19) {-radix decimal} /processor_tb/i_processor/RegsMIPS/regs(20) {-radix decimal} /processor_tb/i_processor/RegsMIPS/regs(21) {-radix decimal} /processor_tb/i_processor/RegsMIPS/regs(22) {-radix decimal} /processor_tb/i_processor/RegsMIPS/regs(23) {-radix decimal} /processor_tb/i_processor/RegsMIPS/regs(24) {-radix decimal} /processor_tb/i_processor/RegsMIPS/regs(25) {-radix decimal} /processor_tb/i_processor/RegsMIPS/regs(26) {-radix decimal} /processor_tb/i_processor/RegsMIPS/regs(27) {-radix decimal} /processor_tb/i_processor/RegsMIPS/regs(28) {-radix decimal} /processor_tb/i_processor/RegsMIPS/regs(29) {-radix decimal} /processor_tb/i_processor/RegsMIPS/regs(30) {-radix decimal} /processor_tb/i_processor/RegsMIPS/regs(31) {-radix decimal}} /processor_tb/i_processor/RegsMIPS/regs
TreeUpdate [SetDefaultTree]
WaveRestoreCursors {{Cursor 1} {72 ns} 0}
quietly wave cursor active 1
configure wave -namecolwidth 284
configure wave -valuecolwidth 100
configure wave -justifyvalue left
configure wave -signalnamewidth 0
configure wave -snapdistance 10
configure wave -datasetprefix 0
configure wave -rowmargin 4
configure wave -childrowmargin 2
configure wave -gridoffset 0
configure wave -gridperiod 1
configure wave -griddelta 40
configure wave -timeline 0
configure wave -timelineunits ns
update
WaveRestoreZoom {0 ns} {456 ns}
