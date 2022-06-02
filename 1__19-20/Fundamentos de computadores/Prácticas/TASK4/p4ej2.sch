<?xml version="1.0" encoding="UTF-8"?>
<drawing version="7">
    <attr value="spartan3" name="DeviceFamilyName">
        <trait delete="all:0" />
        <trait editname="all:0" />
        <trait edittrait="all:0" />
    </attr>
    <netlist>
        <signal name="XLXN_6" />
        <signal name="XLXN_48" />
        <signal name="XLXN_49" />
        <signal name="XLXN_50" />
        <signal name="XLXN_51" />
        <signal name="XLXN_52" />
        <signal name="XLXN_53" />
        <signal name="XLXN_54" />
        <signal name="XLXN_55" />
        <signal name="XLXN_56" />
        <signal name="Q0" />
        <signal name="CLK" />
        <signal name="CLR" />
        <signal name="XLXN_67" />
        <signal name="S0" />
        <signal name="Q2" />
        <signal name="Q1" />
        <signal name="S1" />
        <signal name="RD" />
        <signal name="AD" />
        <signal name="VD" />
        <signal name="RI" />
        <signal name="VI" />
        <signal name="AI" />
        <signal name="XLXN_137" />
        <signal name="XLXN_138" />
        <signal name="XLXN_139" />
        <signal name="XLXN_140" />
        <signal name="XLXN_141" />
        <signal name="XLXN_142" />
        <signal name="XLXN_143" />
        <signal name="XLXN_144" />
        <signal name="XLXN_145" />
        <signal name="XLXN_146" />
        <signal name="XLXN_147" />
        <signal name="XLXN_148" />
        <signal name="XLXN_149" />
        <signal name="XLXN_150" />
        <signal name="XLXN_151" />
        <signal name="XLXN_152" />
        <port polarity="Input" name="CLK" />
        <port polarity="Input" name="CLR" />
        <port polarity="Input" name="S0" />
        <port polarity="Input" name="S1" />
        <port polarity="Output" name="RD" />
        <port polarity="Output" name="AD" />
        <port polarity="Output" name="VD" />
        <port polarity="Output" name="RI" />
        <port polarity="Output" name="VI" />
        <port polarity="Output" name="AI" />
        <blockdef name="rom32x1">
            <timestamp>2000-1-1T10:10:10</timestamp>
            <line x2="320" y1="-384" y2="-384" x1="384" />
            <line x2="64" y1="-192" y2="-192" x1="0" />
            <line x2="64" y1="-384" y2="-384" x1="0" />
            <line x2="64" y1="-320" y2="-320" x1="0" />
            <line x2="64" y1="-256" y2="-256" x1="0" />
            <line x2="64" y1="-128" y2="-128" x1="0" />
            <rect width="256" x="64" y="-448" height="384" />
        </blockdef>
        <blockdef name="fdc">
            <timestamp>2000-1-1T10:10:10</timestamp>
            <line x2="64" y1="-128" y2="-128" x1="0" />
            <line x2="64" y1="-32" y2="-32" x1="0" />
            <line x2="64" y1="-256" y2="-256" x1="0" />
            <line x2="320" y1="-256" y2="-256" x1="384" />
            <rect width="256" x="64" y="-320" height="256" />
            <line x2="80" y1="-112" y2="-128" x1="64" />
            <line x2="64" y1="-128" y2="-144" x1="80" />
            <line x2="192" y1="-64" y2="-32" x1="192" />
            <line x2="64" y1="-32" y2="-32" x1="192" />
        </blockdef>
        <block symbolname="rom32x1" name="XLXI_13">
            <attr value="ffff0000" name="INIT">
                <trait verilog="all:0 dp:1nosynth wsynop:1 wsynth:1" />
                <trait vhdl="all:0 gm:1nosynth wa:1 wd:1" />
                <trait valuetype="BitVector 32 h" />
            </attr>
            <blockpin signalname="S0" name="A0" />
            <blockpin signalname="S1" name="A1" />
            <blockpin signalname="Q0" name="A2" />
            <blockpin signalname="Q1" name="A3" />
            <blockpin signalname="Q2" name="A4" />
            <blockpin signalname="RD" name="O" />
        </block>
        <block symbolname="rom32x1" name="XLXI_14">
            <attr value="0f0a0f0c" name="INIT">
                <trait verilog="all:0 dp:1nosynth wsynop:1 wsynth:1" />
                <trait vhdl="all:0 gm:1nosynth wa:1 wd:1" />
                <trait valuetype="BitVector 32 h" />
            </attr>
            <blockpin signalname="S0" name="A0" />
            <blockpin signalname="S1" name="A1" />
            <blockpin signalname="Q0" name="A2" />
            <blockpin signalname="Q1" name="A3" />
            <blockpin signalname="Q2" name="A4" />
            <blockpin signalname="XLXN_54" name="O" />
        </block>
        <block symbolname="rom32x1" name="XLXI_15">
            <attr value="0ff00ff0" name="INIT">
                <trait verilog="all:0 dp:1nosynth wsynop:1 wsynth:1" />
                <trait vhdl="all:0 gm:1nosynth wa:1 wd:1" />
                <trait valuetype="BitVector 32 h" />
            </attr>
            <blockpin signalname="S0" name="A0" />
            <blockpin signalname="S1" name="A1" />
            <blockpin signalname="Q0" name="A2" />
            <blockpin signalname="Q1" name="A3" />
            <blockpin signalname="Q2" name="A4" />
            <blockpin signalname="XLXN_55" name="O" />
        </block>
        <block symbolname="rom32x1" name="XLXI_16">
            <attr value="0ffff000" name="INIT">
                <trait verilog="all:0 dp:1nosynth wsynop:1 wsynth:1" />
                <trait vhdl="all:0 gm:1nosynth wa:1 wd:1" />
                <trait valuetype="BitVector 32 h" />
            </attr>
            <blockpin signalname="S0" name="A0" />
            <blockpin signalname="S1" name="A1" />
            <blockpin signalname="Q0" name="A2" />
            <blockpin signalname="Q1" name="A3" />
            <blockpin signalname="Q2" name="A4" />
            <blockpin signalname="XLXN_56" name="O" />
        </block>
        <block symbolname="rom32x1" name="XLXI_17">
            <attr value="0000f000" name="INIT">
                <trait verilog="all:0 dp:1nosynth wsynop:1 wsynth:1" />
                <trait vhdl="all:0 gm:1nosynth wa:1 wd:1" />
                <trait valuetype="BitVector 32 h" />
            </attr>
            <blockpin signalname="S0" name="A0" />
            <blockpin signalname="S1" name="A1" />
            <blockpin signalname="Q0" name="A2" />
            <blockpin signalname="Q1" name="A3" />
            <blockpin signalname="Q2" name="A4" />
            <blockpin signalname="AD" name="O" />
        </block>
        <block symbolname="rom32x1" name="XLXI_18">
            <attr value="00000fff" name="INIT">
                <trait verilog="all:0 dp:1nosynth wsynop:1 wsynth:1" />
                <trait vhdl="all:0 gm:1nosynth wa:1 wd:1" />
                <trait valuetype="BitVector 32 h" />
            </attr>
            <blockpin signalname="S0" name="A0" />
            <blockpin signalname="S1" name="A1" />
            <blockpin signalname="Q0" name="A2" />
            <blockpin signalname="Q1" name="A3" />
            <blockpin signalname="Q2" name="A4" />
            <blockpin signalname="VD" name="O" />
        </block>
        <block symbolname="rom32x1" name="XLXI_19">
            <attr value="0000ffff" name="INIT">
                <trait verilog="all:0 dp:1nosynth wsynop:1 wsynth:1" />
                <trait vhdl="all:0 gm:1nosynth wa:1 wd:1" />
                <trait valuetype="BitVector 32 h" />
            </attr>
            <blockpin signalname="S0" name="A0" />
            <blockpin signalname="S1" name="A1" />
            <blockpin signalname="Q0" name="A2" />
            <blockpin signalname="Q1" name="A3" />
            <blockpin signalname="Q2" name="A4" />
            <blockpin signalname="RI" name="O" />
        </block>
        <block symbolname="rom32x1" name="XLXI_20">
            <attr value="f0000000" name="INIT">
                <trait verilog="all:0 dp:1nosynth wsynop:1 wsynth:1" />
                <trait vhdl="all:0 gm:1nosynth wa:1 wd:1" />
                <trait valuetype="BitVector 32 h" />
            </attr>
            <blockpin signalname="S0" name="A0" />
            <blockpin signalname="S1" name="A1" />
            <blockpin signalname="Q0" name="A2" />
            <blockpin signalname="Q1" name="A3" />
            <blockpin signalname="Q2" name="A4" />
            <blockpin signalname="AI" name="O" />
        </block>
        <block symbolname="rom32x1" name="XLXI_21">
            <attr value="0fff0000" name="INIT">
                <trait verilog="all:0 dp:1nosynth wsynop:1 wsynth:1" />
                <trait vhdl="all:0 gm:1nosynth wa:1 wd:1" />
                <trait valuetype="BitVector 32 h" />
            </attr>
            <blockpin signalname="S0" name="A0" />
            <blockpin signalname="S1" name="A1" />
            <blockpin signalname="Q0" name="A2" />
            <blockpin signalname="Q1" name="A3" />
            <blockpin signalname="Q2" name="A4" />
            <blockpin signalname="VI" name="O" />
        </block>
        <block symbolname="fdc" name="XLXI_22">
            <blockpin signalname="CLK" name="C" />
            <blockpin signalname="CLR" name="CLR" />
            <blockpin signalname="XLXN_54" name="D" />
            <blockpin signalname="Q0" name="Q" />
        </block>
        <block symbolname="fdc" name="XLXI_23">
            <blockpin signalname="CLK" name="C" />
            <blockpin signalname="CLR" name="CLR" />
            <blockpin signalname="XLXN_55" name="D" />
            <blockpin signalname="Q1" name="Q" />
        </block>
        <block symbolname="fdc" name="XLXI_24">
            <blockpin signalname="CLK" name="C" />
            <blockpin signalname="CLR" name="CLR" />
            <blockpin signalname="XLXN_56" name="D" />
            <blockpin signalname="Q2" name="Q" />
        </block>
    </netlist>
    <sheet sheetnum="1" width="3520" height="2720">
        <instance x="528" y="1040" name="XLXI_14" orien="R0">
            <attrtext style="fontsize:28;fontname:Arial;displayformat:NAMEEQUALSVALUE" attrname="INIT" x="0" y="-532" type="instance" />
        </instance>
        <instance x="528" y="1552" name="XLXI_15" orien="R0">
            <attrtext style="fontsize:28;fontname:Arial;displayformat:NAMEEQUALSVALUE" attrname="INIT" x="0" y="-532" type="instance" />
        </instance>
        <instance x="2032" y="1040" name="XLXI_18" orien="R0">
            <attrtext style="fontsize:28;fontname:Arial;displayformat:NAMEEQUALSVALUE" attrname="INIT" x="0" y="-532" type="instance" />
        </instance>
        <instance x="2016" y="1552" name="XLXI_19" orien="R0">
            <attrtext style="fontsize:28;fontname:Arial;displayformat:NAMEEQUALSVALUE" attrname="INIT" x="0" y="-532" type="instance" />
        </instance>
        <instance x="2016" y="2080" name="XLXI_20" orien="R0">
            <attrtext style="fontsize:28;fontname:Arial;displayformat:NAMEEQUALSVALUE" attrname="INIT" x="0" y="-532" type="instance" />
        </instance>
        <instance x="2016" y="2608" name="XLXI_21" orien="R0">
            <attrtext style="fontsize:28;fontname:Arial;displayformat:NAMEEQUALSVALUE" attrname="INIT" x="0" y="-532" type="instance" />
        </instance>
        <branch name="XLXN_54">
            <wire x2="976" y1="656" y2="656" x1="912" />
        </branch>
        <branch name="XLXN_55">
            <wire x2="992" y1="1168" y2="1168" x1="912" />
        </branch>
        <branch name="XLXN_56">
            <wire x2="1008" y1="1728" y2="1728" x1="880" />
        </branch>
        <instance x="976" y="912" name="XLXI_22" orien="R0" />
        <branch name="Q0">
            <attrtext style="alignment:SOFT-LEFT;fontsize:28;fontname:Arial" attrname="Name" x="1440" y="656" type="branch" />
            <wire x2="1440" y1="656" y2="656" x1="1360" />
        </branch>
        <instance x="992" y="1424" name="XLXI_23" orien="R0" />
        <branch name="Q1">
            <attrtext style="alignment:SOFT-LEFT;fontsize:28;fontname:Arial" attrname="Name" x="1424" y="1168" type="branch" />
            <wire x2="1424" y1="1168" y2="1168" x1="1376" />
        </branch>
        <instance x="1008" y="1984" name="XLXI_24" orien="R0" />
        <branch name="Q2">
            <attrtext style="alignment:SOFT-LEFT;fontsize:28;fontname:Arial" attrname="Name" x="1424" y="1728" type="branch" />
            <wire x2="1424" y1="1728" y2="1728" x1="1392" />
        </branch>
        <branch name="CLK">
            <wire x2="928" y1="2144" y2="2144" x1="816" />
            <wire x2="976" y1="784" y2="784" x1="928" />
            <wire x2="928" y1="784" y2="800" x1="928" />
            <wire x2="928" y1="800" y2="1296" x1="928" />
            <wire x2="928" y1="1296" y2="1856" x1="928" />
            <wire x2="928" y1="1856" y2="2144" x1="928" />
            <wire x2="1008" y1="1856" y2="1856" x1="928" />
            <wire x2="992" y1="1296" y2="1296" x1="928" />
        </branch>
        <instance x="496" y="2112" name="XLXI_16" orien="R0">
            <attrtext style="fontsize:28;fontname:Arial;displayformat:NAMEEQUALSVALUE" attrname="INIT" x="0" y="-532" type="instance" />
        </instance>
        <branch name="CLR">
            <wire x2="976" y1="880" y2="896" x1="976" />
            <wire x2="976" y1="896" y2="1392" x1="976" />
            <wire x2="992" y1="1392" y2="1392" x1="976" />
            <wire x2="976" y1="1392" y2="1952" x1="976" />
            <wire x2="976" y1="1952" y2="2144" x1="976" />
            <wire x2="1072" y1="2144" y2="2144" x1="976" />
            <wire x2="992" y1="1952" y2="1952" x1="976" />
            <wire x2="1008" y1="1952" y2="1952" x1="992" />
        </branch>
        <branch name="Q0">
            <attrtext style="alignment:SOFT-RIGHT;fontsize:28;fontname:Arial" attrname="Name" x="496" y="272" type="branch" />
            <wire x2="512" y1="272" y2="272" x1="496" />
            <wire x2="512" y1="272" y2="288" x1="512" />
            <wire x2="528" y1="288" y2="288" x1="512" />
        </branch>
        <branch name="Q1">
            <attrtext style="alignment:SOFT-RIGHT;fontsize:28;fontname:Arial" attrname="Name" x="496" y="336" type="branch" />
            <wire x2="512" y1="336" y2="336" x1="496" />
            <wire x2="512" y1="336" y2="352" x1="512" />
            <wire x2="528" y1="352" y2="352" x1="512" />
        </branch>
        <branch name="S0">
            <attrtext style="alignment:SOFT-RIGHT;fontsize:28;fontname:Arial" attrname="Name" x="496" y="656" type="branch" />
            <wire x2="528" y1="656" y2="656" x1="496" />
        </branch>
        <branch name="Q2">
            <attrtext style="alignment:SOFT-RIGHT;fontsize:28;fontname:Arial" attrname="Name" x="496" y="400" type="branch" />
            <wire x2="512" y1="400" y2="400" x1="496" />
            <wire x2="512" y1="400" y2="416" x1="512" />
            <wire x2="528" y1="416" y2="416" x1="512" />
        </branch>
        <branch name="S0">
            <attrtext style="alignment:SOFT-RIGHT;fontsize:28;fontname:Arial" attrname="Name" x="2000" y="160" type="branch" />
            <wire x2="2016" y1="160" y2="160" x1="2000" />
            <wire x2="2016" y1="160" y2="208" x1="2016" />
            <wire x2="2032" y1="208" y2="208" x1="2016" />
        </branch>
        <branch name="Q0">
            <attrtext style="alignment:SOFT-RIGHT;fontsize:28;fontname:Arial" attrname="Name" x="2000" y="288" type="branch" />
            <wire x2="2016" y1="288" y2="288" x1="2000" />
            <wire x2="2016" y1="288" y2="336" x1="2016" />
            <wire x2="2032" y1="336" y2="336" x1="2016" />
        </branch>
        <branch name="Q1">
            <attrtext style="alignment:SOFT-RIGHT;fontsize:28;fontname:Arial" attrname="Name" x="2000" y="352" type="branch" />
            <wire x2="2016" y1="352" y2="352" x1="2000" />
            <wire x2="2016" y1="352" y2="400" x1="2016" />
            <wire x2="2032" y1="400" y2="400" x1="2016" />
        </branch>
        <branch name="Q2">
            <attrtext style="alignment:SOFT-RIGHT;fontsize:28;fontname:Arial" attrname="Name" x="2000" y="416" type="branch" />
            <wire x2="2016" y1="416" y2="416" x1="2000" />
            <wire x2="2016" y1="416" y2="464" x1="2016" />
            <wire x2="2032" y1="464" y2="464" x1="2016" />
        </branch>
        <branch name="S1">
            <attrtext style="alignment:SOFT-RIGHT;fontsize:28;fontname:Arial" attrname="Name" x="2000" y="224" type="branch" />
            <wire x2="2016" y1="224" y2="224" x1="2000" />
            <wire x2="2016" y1="224" y2="272" x1="2016" />
            <wire x2="2032" y1="272" y2="272" x1="2016" />
        </branch>
        <branch name="S0">
            <attrtext style="alignment:SOFT-RIGHT;fontsize:28;fontname:Arial" attrname="Name" x="2000" y="656" type="branch" />
            <wire x2="2032" y1="656" y2="656" x1="2000" />
        </branch>
        <branch name="S1">
            <attrtext style="alignment:SOFT-RIGHT;fontsize:28;fontname:Arial" attrname="Name" x="2000" y="720" type="branch" />
            <wire x2="2032" y1="720" y2="720" x1="2000" />
        </branch>
        <branch name="Q0">
            <attrtext style="alignment:SOFT-RIGHT;fontsize:28;fontname:Arial" attrname="Name" x="2000" y="784" type="branch" />
            <wire x2="2032" y1="784" y2="784" x1="2000" />
        </branch>
        <branch name="Q1">
            <attrtext style="alignment:SOFT-RIGHT;fontsize:28;fontname:Arial" attrname="Name" x="2000" y="848" type="branch" />
            <wire x2="2032" y1="848" y2="848" x1="2000" />
        </branch>
        <branch name="Q2">
            <attrtext style="alignment:SOFT-RIGHT;fontsize:28;fontname:Arial" attrname="Name" x="2000" y="912" type="branch" />
            <wire x2="2032" y1="912" y2="912" x1="2000" />
        </branch>
        <branch name="S0">
            <attrtext style="alignment:SOFT-RIGHT;fontsize:28;fontname:Arial" attrname="Name" x="1984" y="1168" type="branch" />
            <wire x2="2016" y1="1168" y2="1168" x1="1984" />
        </branch>
        <branch name="S1">
            <attrtext style="alignment:SOFT-RIGHT;fontsize:28;fontname:Arial" attrname="Name" x="1984" y="1232" type="branch" />
            <wire x2="2016" y1="1232" y2="1232" x1="1984" />
        </branch>
        <branch name="Q0">
            <attrtext style="alignment:SOFT-RIGHT;fontsize:28;fontname:Arial" attrname="Name" x="1984" y="1296" type="branch" />
            <wire x2="2016" y1="1296" y2="1296" x1="1984" />
        </branch>
        <branch name="Q1">
            <attrtext style="alignment:SOFT-RIGHT;fontsize:28;fontname:Arial" attrname="Name" x="1984" y="1360" type="branch" />
            <wire x2="2016" y1="1360" y2="1360" x1="1984" />
        </branch>
        <branch name="Q2">
            <attrtext style="alignment:SOFT-RIGHT;fontsize:28;fontname:Arial" attrname="Name" x="1984" y="1424" type="branch" />
            <wire x2="2016" y1="1424" y2="1424" x1="1984" />
        </branch>
        <branch name="S0">
            <attrtext style="alignment:SOFT-RIGHT;fontsize:28;fontname:Arial" attrname="Name" x="1984" y="1696" type="branch" />
            <wire x2="2016" y1="1696" y2="1696" x1="1984" />
        </branch>
        <branch name="S1">
            <attrtext style="alignment:SOFT-RIGHT;fontsize:28;fontname:Arial" attrname="Name" x="1984" y="1760" type="branch" />
            <wire x2="2016" y1="1760" y2="1760" x1="1984" />
        </branch>
        <branch name="Q0">
            <attrtext style="alignment:SOFT-RIGHT;fontsize:28;fontname:Arial" attrname="Name" x="1984" y="1824" type="branch" />
            <wire x2="2016" y1="1824" y2="1824" x1="1984" />
        </branch>
        <branch name="Q1">
            <attrtext style="alignment:SOFT-RIGHT;fontsize:28;fontname:Arial" attrname="Name" x="1984" y="1888" type="branch" />
            <wire x2="2016" y1="1888" y2="1888" x1="1984" />
        </branch>
        <branch name="Q2">
            <attrtext style="alignment:SOFT-RIGHT;fontsize:28;fontname:Arial" attrname="Name" x="1984" y="1952" type="branch" />
            <wire x2="2016" y1="1952" y2="1952" x1="1984" />
        </branch>
        <branch name="S0">
            <attrtext style="alignment:SOFT-RIGHT;fontsize:28;fontname:Arial" attrname="Name" x="1984" y="2224" type="branch" />
            <wire x2="2016" y1="2224" y2="2224" x1="1984" />
        </branch>
        <branch name="S1">
            <attrtext style="alignment:SOFT-RIGHT;fontsize:28;fontname:Arial" attrname="Name" x="1984" y="2288" type="branch" />
            <wire x2="2016" y1="2288" y2="2288" x1="1984" />
        </branch>
        <branch name="Q0">
            <attrtext style="alignment:SOFT-RIGHT;fontsize:28;fontname:Arial" attrname="Name" x="1984" y="2352" type="branch" />
            <wire x2="2016" y1="2352" y2="2352" x1="1984" />
        </branch>
        <branch name="Q1">
            <attrtext style="alignment:SOFT-RIGHT;fontsize:28;fontname:Arial" attrname="Name" x="1984" y="2416" type="branch" />
            <wire x2="2016" y1="2416" y2="2416" x1="1984" />
        </branch>
        <branch name="Q2">
            <attrtext style="alignment:SOFT-RIGHT;fontsize:28;fontname:Arial" attrname="Name" x="1984" y="2480" type="branch" />
            <wire x2="2016" y1="2480" y2="2480" x1="1984" />
        </branch>
        <branch name="S0">
            <attrtext style="alignment:SOFT-RIGHT;fontsize:28;fontname:Arial" attrname="Name" x="464" y="1728" type="branch" />
            <wire x2="496" y1="1728" y2="1728" x1="464" />
        </branch>
        <branch name="S1">
            <attrtext style="alignment:SOFT-RIGHT;fontsize:28;fontname:Arial" attrname="Name" x="464" y="1792" type="branch" />
            <wire x2="496" y1="1792" y2="1792" x1="464" />
        </branch>
        <branch name="Q0">
            <attrtext style="alignment:SOFT-RIGHT;fontsize:28;fontname:Arial" attrname="Name" x="464" y="1856" type="branch" />
            <wire x2="496" y1="1856" y2="1856" x1="464" />
        </branch>
        <branch name="Q1">
            <attrtext style="alignment:SOFT-RIGHT;fontsize:28;fontname:Arial" attrname="Name" x="464" y="1920" type="branch" />
            <wire x2="496" y1="1920" y2="1920" x1="464" />
        </branch>
        <branch name="Q2">
            <attrtext style="alignment:SOFT-RIGHT;fontsize:28;fontname:Arial" attrname="Name" x="464" y="1984" type="branch" />
            <wire x2="496" y1="1984" y2="1984" x1="464" />
        </branch>
        <branch name="S0">
            <attrtext style="alignment:SOFT-RIGHT;fontsize:28;fontname:Arial" attrname="Name" x="496" y="1168" type="branch" />
            <wire x2="528" y1="1168" y2="1168" x1="496" />
        </branch>
        <branch name="S1">
            <attrtext style="alignment:SOFT-RIGHT;fontsize:28;fontname:Arial" attrname="Name" x="496" y="1232" type="branch" />
            <wire x2="528" y1="1232" y2="1232" x1="496" />
        </branch>
        <branch name="Q0">
            <attrtext style="alignment:SOFT-RIGHT;fontsize:28;fontname:Arial" attrname="Name" x="496" y="1296" type="branch" />
            <wire x2="528" y1="1296" y2="1296" x1="496" />
        </branch>
        <branch name="Q1">
            <attrtext style="alignment:SOFT-RIGHT;fontsize:28;fontname:Arial" attrname="Name" x="496" y="1360" type="branch" />
            <wire x2="528" y1="1360" y2="1360" x1="496" />
        </branch>
        <branch name="Q2">
            <attrtext style="alignment:SOFT-RIGHT;fontsize:28;fontname:Arial" attrname="Name" x="496" y="1424" type="branch" />
            <wire x2="528" y1="1424" y2="1424" x1="496" />
        </branch>
        <branch name="S1">
            <attrtext style="alignment:SOFT-RIGHT;fontsize:28;fontname:Arial" attrname="Name" x="496" y="720" type="branch" />
            <wire x2="528" y1="720" y2="720" x1="496" />
        </branch>
        <branch name="Q0">
            <attrtext style="alignment:SOFT-RIGHT;fontsize:28;fontname:Arial" attrname="Name" x="496" y="784" type="branch" />
            <wire x2="528" y1="784" y2="784" x1="496" />
        </branch>
        <branch name="Q2">
            <attrtext style="alignment:SOFT-RIGHT;fontsize:28;fontname:Arial" attrname="Name" x="496" y="912" type="branch" />
            <wire x2="528" y1="912" y2="912" x1="496" />
        </branch>
        <branch name="Q1">
            <attrtext style="alignment:SOFT-RIGHT;fontsize:28;fontname:Arial" attrname="Name" x="496" y="848" type="branch" />
            <wire x2="528" y1="848" y2="848" x1="496" />
        </branch>
        <iomarker fontsize="28" x="816" y="2144" name="CLK" orien="R180" />
        <iomarker fontsize="28" x="1072" y="2144" name="CLR" orien="R0" />
        <branch name="RD">
            <wire x2="928" y1="160" y2="160" x1="912" />
            <wire x2="944" y1="144" y2="144" x1="928" />
            <wire x2="928" y1="144" y2="160" x1="928" />
        </branch>
        <branch name="AD">
            <wire x2="2448" y1="208" y2="208" x1="2416" />
            <wire x2="2464" y1="160" y2="160" x1="2448" />
            <wire x2="2448" y1="160" y2="208" x1="2448" />
        </branch>
        <branch name="VD">
            <wire x2="2464" y1="656" y2="656" x1="2416" />
        </branch>
        <branch name="RI">
            <wire x2="2448" y1="1168" y2="1168" x1="2400" />
        </branch>
        <branch name="VI">
            <wire x2="2432" y1="2224" y2="2224" x1="2400" />
        </branch>
        <branch name="AI">
            <wire x2="2448" y1="1696" y2="1696" x1="2400" />
        </branch>
        <iomarker fontsize="28" x="944" y="144" name="RD" orien="R0" />
        <iomarker fontsize="28" x="2464" y="160" name="AD" orien="R0" />
        <iomarker fontsize="28" x="2464" y="656" name="VD" orien="R0" />
        <iomarker fontsize="28" x="2448" y="1168" name="RI" orien="R0" />
        <iomarker fontsize="28" x="2448" y="1696" name="AI" orien="R0" />
        <iomarker fontsize="28" x="2432" y="2224" name="VI" orien="R0" />
        <iomarker fontsize="28" x="480" y="144" name="S0" orien="R180" />
        <iomarker fontsize="28" x="480" y="208" name="S1" orien="R180" />
        <instance x="2032" y="592" name="XLXI_17" orien="R0">
            <attrtext style="fontsize:28;fontname:Arial;displayformat:NAMEEQUALSVALUE" attrname="INIT" x="0" y="-532" type="instance" />
        </instance>
        <branch name="S1">
            <wire x2="496" y1="208" y2="208" x1="480" />
            <wire x2="496" y1="208" y2="224" x1="496" />
            <wire x2="528" y1="224" y2="224" x1="496" />
        </branch>
        <branch name="S0">
            <wire x2="496" y1="144" y2="144" x1="480" />
            <wire x2="496" y1="144" y2="160" x1="496" />
            <wire x2="528" y1="160" y2="160" x1="496" />
        </branch>
        <instance x="528" y="544" name="XLXI_13" orien="R0">
            <attrtext style="fontsize:28;fontname:Arial;displayformat:NAMEEQUALSVALUE" attrname="INIT" x="0" y="-532" type="instance" />
        </instance>
    </sheet>
</drawing>