<?xml version="1.0" encoding="UTF-8"?>
<drawing version="7">
    <attr value="spartan3" name="DeviceFamilyName">
        <trait delete="all:0" />
        <trait editname="all:0" />
        <trait edittrait="all:0" />
    </attr>
    <netlist>
        <signal name="RD" />
        <signal name="XLXN_2" />
        <signal name="XLXN_3" />
        <signal name="XLXN_4" />
        <signal name="XLXN_5" />
        <signal name="XLXN_6" />
        <signal name="XLXN_7" />
        <signal name="XLXN_8" />
        <signal name="XLXN_9" />
        <signal name="XLXN_12" />
        <signal name="XLXN_13" />
        <signal name="XLXN_15" />
        <signal name="XLXN_16" />
        <signal name="S0" />
        <signal name="S1" />
        <signal name="CLK" />
        <signal name="CLR" />
        <signal name="XLXN_26" />
        <signal name="RI" />
        <signal name="AD" />
        <signal name="AI" />
        <signal name="XLXN_35" />
        <signal name="XLXN_36" />
        <signal name="XLXN_37" />
        <signal name="XLXN_38" />
        <signal name="XLXN_39" />
        <signal name="XLXN_40" />
        <signal name="XLXN_42" />
        <signal name="XLXN_43" />
        <signal name="XLXN_44" />
        <signal name="XLXN_46" />
        <signal name="XLXN_47" />
        <signal name="VD" />
        <signal name="VI" />
        <port polarity="Output" name="RD" />
        <port polarity="Input" name="S0" />
        <port polarity="Input" name="S1" />
        <port polarity="Input" name="CLK" />
        <port polarity="Input" name="CLR" />
        <port polarity="Output" name="RI" />
        <port polarity="Output" name="AD" />
        <port polarity="Output" name="AI" />
        <port polarity="Output" name="VD" />
        <port polarity="Output" name="VI" />
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
        <blockdef name="xor2">
            <timestamp>2000-1-1T10:10:10</timestamp>
            <line x2="64" y1="-64" y2="-64" x1="0" />
            <line x2="60" y1="-128" y2="-128" x1="0" />
            <line x2="208" y1="-96" y2="-96" x1="256" />
            <arc ex="44" ey="-144" sx="48" sy="-48" r="56" cx="16" cy="-96" />
            <arc ex="64" ey="-144" sx="64" sy="-48" r="56" cx="32" cy="-96" />
            <line x2="64" y1="-144" y2="-144" x1="128" />
            <line x2="64" y1="-48" y2="-48" x1="128" />
            <arc ex="128" ey="-144" sx="208" sy="-96" r="88" cx="132" cy="-56" />
            <arc ex="208" ey="-96" sx="128" sy="-48" r="88" cx="132" cy="-136" />
        </blockdef>
        <blockdef name="and3b1">
            <timestamp>2000-1-1T10:10:10</timestamp>
            <line x2="40" y1="-64" y2="-64" x1="0" />
            <circle r="12" cx="52" cy="-64" />
            <line x2="64" y1="-128" y2="-128" x1="0" />
            <line x2="64" y1="-192" y2="-192" x1="0" />
            <line x2="192" y1="-128" y2="-128" x1="256" />
            <line x2="64" y1="-64" y2="-192" x1="64" />
            <arc ex="144" ey="-176" sx="144" sy="-80" r="48" cx="144" cy="-128" />
            <line x2="64" y1="-80" y2="-80" x1="144" />
            <line x2="144" y1="-176" y2="-176" x1="64" />
        </blockdef>
        <blockdef name="and2">
            <timestamp>2000-1-1T10:10:10</timestamp>
            <line x2="64" y1="-64" y2="-64" x1="0" />
            <line x2="64" y1="-128" y2="-128" x1="0" />
            <line x2="192" y1="-96" y2="-96" x1="256" />
            <arc ex="144" ey="-144" sx="144" sy="-48" r="48" cx="144" cy="-96" />
            <line x2="64" y1="-48" y2="-48" x1="144" />
            <line x2="144" y1="-144" y2="-144" x1="64" />
            <line x2="64" y1="-48" y2="-144" x1="64" />
        </blockdef>
        <blockdef name="nand2">
            <timestamp>2000-1-1T10:10:10</timestamp>
            <line x2="64" y1="-64" y2="-64" x1="0" />
            <line x2="64" y1="-128" y2="-128" x1="0" />
            <line x2="216" y1="-96" y2="-96" x1="256" />
            <circle r="12" cx="204" cy="-96" />
            <line x2="64" y1="-48" y2="-144" x1="64" />
            <line x2="144" y1="-144" y2="-144" x1="64" />
            <line x2="64" y1="-48" y2="-48" x1="144" />
            <arc ex="144" ey="-144" sx="144" sy="-48" r="48" cx="144" cy="-96" />
        </blockdef>
        <blockdef name="or2">
            <timestamp>2000-1-1T10:10:10</timestamp>
            <line x2="64" y1="-64" y2="-64" x1="0" />
            <line x2="64" y1="-128" y2="-128" x1="0" />
            <line x2="192" y1="-96" y2="-96" x1="256" />
            <arc ex="192" ey="-96" sx="112" sy="-48" r="88" cx="116" cy="-136" />
            <arc ex="48" ey="-144" sx="48" sy="-48" r="56" cx="16" cy="-96" />
            <line x2="48" y1="-144" y2="-144" x1="112" />
            <arc ex="112" ey="-144" sx="192" sy="-96" r="88" cx="116" cy="-56" />
            <line x2="48" y1="-48" y2="-48" x1="112" />
        </blockdef>
        <blockdef name="and2b1">
            <timestamp>2000-1-1T10:10:10</timestamp>
            <line x2="64" y1="-48" y2="-144" x1="64" />
            <line x2="144" y1="-144" y2="-144" x1="64" />
            <line x2="64" y1="-48" y2="-48" x1="144" />
            <arc ex="144" ey="-144" sx="144" sy="-48" r="48" cx="144" cy="-96" />
            <line x2="192" y1="-96" y2="-96" x1="256" />
            <line x2="64" y1="-128" y2="-128" x1="0" />
            <line x2="40" y1="-64" y2="-64" x1="0" />
            <circle r="12" cx="52" cy="-64" />
        </blockdef>
        <blockdef name="or3">
            <timestamp>2000-1-1T10:10:10</timestamp>
            <line x2="48" y1="-64" y2="-64" x1="0" />
            <line x2="72" y1="-128" y2="-128" x1="0" />
            <line x2="48" y1="-192" y2="-192" x1="0" />
            <line x2="192" y1="-128" y2="-128" x1="256" />
            <arc ex="192" ey="-128" sx="112" sy="-80" r="88" cx="116" cy="-168" />
            <arc ex="48" ey="-176" sx="48" sy="-80" r="56" cx="16" cy="-128" />
            <line x2="48" y1="-64" y2="-80" x1="48" />
            <line x2="48" y1="-192" y2="-176" x1="48" />
            <line x2="48" y1="-80" y2="-80" x1="112" />
            <arc ex="112" ey="-176" sx="192" sy="-128" r="88" cx="116" cy="-88" />
            <line x2="48" y1="-176" y2="-176" x1="112" />
        </blockdef>
        <blockdef name="inv">
            <timestamp>2000-1-1T10:10:10</timestamp>
            <line x2="64" y1="-32" y2="-32" x1="0" />
            <line x2="160" y1="-32" y2="-32" x1="224" />
            <line x2="128" y1="-64" y2="-32" x1="64" />
            <line x2="64" y1="-32" y2="0" x1="128" />
            <line x2="64" y1="0" y2="-64" x1="64" />
            <circle r="16" cx="144" cy="-32" />
        </blockdef>
        <blockdef name="and3">
            <timestamp>2000-1-1T10:10:10</timestamp>
            <line x2="64" y1="-64" y2="-64" x1="0" />
            <line x2="64" y1="-128" y2="-128" x1="0" />
            <line x2="64" y1="-192" y2="-192" x1="0" />
            <line x2="192" y1="-128" y2="-128" x1="256" />
            <line x2="144" y1="-176" y2="-176" x1="64" />
            <line x2="64" y1="-80" y2="-80" x1="144" />
            <arc ex="144" ey="-176" sx="144" sy="-80" r="48" cx="144" cy="-128" />
            <line x2="64" y1="-64" y2="-192" x1="64" />
        </blockdef>
        <block symbolname="fdc" name="XLXI_1">
            <blockpin signalname="CLK" name="C" />
            <blockpin signalname="CLR" name="CLR" />
            <blockpin signalname="XLXN_9" name="D" />
            <blockpin signalname="RD" name="Q" />
        </block>
        <block symbolname="fdc" name="XLXI_2">
            <blockpin signalname="CLK" name="C" />
            <blockpin signalname="CLR" name="CLR" />
            <blockpin signalname="XLXN_5" name="D" />
            <blockpin signalname="XLXN_39" name="Q" />
        </block>
        <block symbolname="fdc" name="XLXI_3">
            <blockpin signalname="CLK" name="C" />
            <blockpin signalname="CLR" name="CLR" />
            <blockpin signalname="XLXN_12" name="D" />
            <blockpin signalname="XLXN_36" name="Q" />
        </block>
        <block symbolname="xor2" name="XLXI_4">
            <blockpin signalname="XLXN_39" name="I0" />
            <blockpin signalname="XLXN_36" name="I1" />
            <blockpin signalname="XLXN_5" name="O" />
        </block>
        <block symbolname="and3b1" name="XLXI_5">
            <blockpin signalname="RD" name="I0" />
            <blockpin signalname="XLXN_39" name="I1" />
            <blockpin signalname="XLXN_36" name="I2" />
            <blockpin signalname="XLXN_7" name="O" />
        </block>
        <block symbolname="and2" name="XLXI_6">
            <blockpin signalname="XLXN_8" name="I0" />
            <blockpin signalname="RD" name="I1" />
            <blockpin signalname="XLXN_6" name="O" />
        </block>
        <block symbolname="nand2" name="XLXI_7">
            <blockpin signalname="XLXN_36" name="I0" />
            <blockpin signalname="XLXN_39" name="I1" />
            <blockpin signalname="XLXN_8" name="O" />
        </block>
        <block symbolname="or2" name="XLXI_8">
            <blockpin signalname="XLXN_6" name="I0" />
            <blockpin signalname="XLXN_7" name="I1" />
            <blockpin signalname="XLXN_9" name="O" />
        </block>
        <block symbolname="and2b1" name="XLXI_9">
            <blockpin signalname="XLXN_36" name="I0" />
            <blockpin signalname="XLXN_13" name="I1" />
            <blockpin signalname="XLXN_12" name="O" />
        </block>
        <block symbolname="or3" name="XLXI_10">
            <blockpin signalname="XLXN_16" name="I0" />
            <blockpin signalname="XLXN_15" name="I1" />
            <blockpin signalname="XLXN_39" name="I2" />
            <blockpin signalname="XLXN_13" name="O" />
        </block>
        <block symbolname="and2b1" name="XLXI_11">
            <blockpin signalname="RD" name="I0" />
            <blockpin signalname="S1" name="I1" />
            <blockpin signalname="XLXN_15" name="O" />
        </block>
        <block symbolname="and2" name="XLXI_12">
            <blockpin signalname="RD" name="I0" />
            <blockpin signalname="S0" name="I1" />
            <blockpin signalname="XLXN_16" name="O" />
        </block>
        <block symbolname="inv" name="XLXI_13">
            <blockpin signalname="RD" name="I" />
            <blockpin signalname="RI" name="O" />
        </block>
        <block symbolname="and3" name="XLXI_14">
            <blockpin signalname="XLXN_36" name="I0" />
            <blockpin signalname="XLXN_39" name="I1" />
            <blockpin signalname="RD" name="I2" />
            <blockpin signalname="AI" name="O" />
        </block>
        <block symbolname="and3b1" name="XLXI_15">
            <blockpin signalname="RD" name="I0" />
            <blockpin signalname="XLXN_36" name="I1" />
            <blockpin signalname="XLXN_39" name="I2" />
            <blockpin signalname="AD" name="O" />
        </block>
        <block symbolname="nand2" name="XLXI_16">
            <blockpin signalname="XLXN_36" name="I0" />
            <blockpin signalname="XLXN_39" name="I1" />
            <blockpin signalname="XLXN_43" name="O" />
        </block>
        <block symbolname="and2" name="XLXI_17">
            <blockpin signalname="XLXN_43" name="I0" />
            <blockpin signalname="RD" name="I1" />
            <blockpin signalname="VI" name="O" />
        </block>
        <block symbolname="and2b1" name="XLXI_18">
            <blockpin signalname="RD" name="I0" />
            <blockpin signalname="XLXN_43" name="I1" />
            <blockpin signalname="VD" name="O" />
        </block>
    </netlist>
    <sheet sheetnum="1" width="3520" height="2720">
        <branch name="XLXN_5">
            <wire x2="1712" y1="960" y2="960" x1="1552" />
        </branch>
        <instance x="1296" y="1056" name="XLXI_4" orien="R0" />
        <instance x="448" y="400" name="XLXI_5" orien="R0" />
        <instance x="400" y="672" name="XLXI_7" orien="R0" />
        <branch name="XLXN_7">
            <wire x2="1312" y1="272" y2="272" x1="704" />
            <wire x2="1312" y1="272" y2="432" x1="1312" />
        </branch>
        <branch name="XLXN_9">
            <wire x2="1712" y1="464" y2="464" x1="1568" />
        </branch>
        <instance x="1312" y="560" name="XLXI_8" orien="R0" />
        <branch name="XLXN_6">
            <wire x2="1312" y1="496" y2="496" x1="1136" />
        </branch>
        <instance x="880" y="592" name="XLXI_6" orien="R0" />
        <branch name="XLXN_8">
            <wire x2="672" y1="576" y2="576" x1="656" />
            <wire x2="880" y1="528" y2="528" x1="672" />
            <wire x2="672" y1="528" y2="576" x1="672" />
        </branch>
        <branch name="XLXN_12">
            <wire x2="1696" y1="1488" y2="1488" x1="1552" />
        </branch>
        <instance x="1296" y="1584" name="XLXI_9" orien="R0" />
        <branch name="XLXN_13">
            <wire x2="1280" y1="1152" y2="1152" x1="1264" />
            <wire x2="1280" y1="1152" y2="1456" x1="1280" />
            <wire x2="1296" y1="1456" y2="1456" x1="1280" />
        </branch>
        <instance x="1008" y="1280" name="XLXI_10" orien="R0" />
        <instance x="496" y="1312" name="XLXI_11" orien="R0" />
        <instance x="496" y="1488" name="XLXI_12" orien="R0" />
        <branch name="XLXN_15">
            <wire x2="880" y1="1216" y2="1216" x1="752" />
            <wire x2="880" y1="1152" y2="1216" x1="880" />
            <wire x2="1008" y1="1152" y2="1152" x1="880" />
        </branch>
        <branch name="XLXN_16">
            <wire x2="1008" y1="1392" y2="1392" x1="752" />
            <wire x2="1008" y1="1216" y2="1392" x1="1008" />
        </branch>
        <branch name="S0">
            <wire x2="496" y1="1360" y2="1360" x1="416" />
        </branch>
        <branch name="S1">
            <wire x2="496" y1="1184" y2="1184" x1="416" />
        </branch>
        <iomarker fontsize="28" x="416" y="1184" name="S1" orien="R180" />
        <iomarker fontsize="28" x="416" y="1360" name="S0" orien="R180" />
        <instance x="1712" y="720" name="XLXI_1" orien="R0" />
        <instance x="1712" y="1216" name="XLXI_2" orien="R0" />
        <instance x="1696" y="1744" name="XLXI_3" orien="R0" />
        <branch name="CLK">
            <wire x2="1616" y1="1952" y2="1952" x1="1536" />
            <wire x2="1712" y1="592" y2="592" x1="1616" />
            <wire x2="1616" y1="592" y2="1088" x1="1616" />
            <wire x2="1616" y1="1088" y2="1616" x1="1616" />
            <wire x2="1616" y1="1616" y2="1952" x1="1616" />
            <wire x2="1696" y1="1616" y2="1616" x1="1616" />
            <wire x2="1712" y1="1088" y2="1088" x1="1616" />
        </branch>
        <branch name="CLR">
            <wire x2="1680" y1="2016" y2="2016" x1="1184" />
            <wire x2="1680" y1="688" y2="1184" x1="1680" />
            <wire x2="1680" y1="1184" y2="1712" x1="1680" />
            <wire x2="1680" y1="1712" y2="2016" x1="1680" />
            <wire x2="1696" y1="1712" y2="1712" x1="1680" />
            <wire x2="1712" y1="1184" y2="1184" x1="1680" />
            <wire x2="1712" y1="688" y2="688" x1="1680" />
        </branch>
        <iomarker fontsize="28" x="1184" y="2016" name="CLR" orien="R180" />
        <iomarker fontsize="28" x="1536" y="1952" name="CLK" orien="R180" />
        <iomarker fontsize="28" x="3264" y="464" name="RD" orien="R0" />
        <instance x="2832" y="352" name="XLXI_13" orien="R0" />
        <branch name="RI">
            <wire x2="3280" y1="320" y2="320" x1="3056" />
        </branch>
        <iomarker fontsize="28" x="3280" y="320" name="RI" orien="R0" />
        <instance x="2896" y="960" name="XLXI_15" orien="R0" />
        <branch name="AD">
            <wire x2="3280" y1="832" y2="832" x1="3152" />
        </branch>
        <instance x="2896" y="1216" name="XLXI_14" orien="R0" />
        <branch name="AI">
            <wire x2="3296" y1="1088" y2="1088" x1="3152" />
        </branch>
        <branch name="XLXN_36">
            <wire x2="448" y1="208" y2="208" x1="32" />
            <wire x2="32" y1="208" y2="608" x1="32" />
            <wire x2="32" y1="608" y2="928" x1="32" />
            <wire x2="1296" y1="928" y2="928" x1="32" />
            <wire x2="32" y1="928" y2="1520" x1="32" />
            <wire x2="32" y1="1520" y2="2144" x1="32" />
            <wire x2="2176" y1="2144" y2="2144" x1="32" />
            <wire x2="1296" y1="1520" y2="1520" x1="32" />
            <wire x2="400" y1="608" y2="608" x1="32" />
            <wire x2="2176" y1="1488" y2="1488" x1="2080" />
            <wire x2="2320" y1="1488" y2="1488" x1="2176" />
            <wire x2="2320" y1="1488" y2="1792" x1="2320" />
            <wire x2="2448" y1="1792" y2="1792" x1="2320" />
            <wire x2="2176" y1="1488" y2="2144" x1="2176" />
            <wire x2="2896" y1="832" y2="832" x1="2320" />
            <wire x2="2320" y1="832" y2="1152" x1="2320" />
            <wire x2="2320" y1="1152" y2="1488" x1="2320" />
            <wire x2="2896" y1="1152" y2="1152" x1="2320" />
        </branch>
        <iomarker fontsize="28" x="3280" y="832" name="AD" orien="R0" />
        <iomarker fontsize="28" x="3296" y="1088" name="AI" orien="R0" />
        <instance x="3008" y="1760" name="XLXI_18" orien="R0" />
        <instance x="3008" y="1984" name="XLXI_17" orien="R0" />
        <instance x="2448" y="1856" name="XLXI_16" orien="R0" />
        <branch name="XLXN_39">
            <wire x2="448" y1="272" y2="272" x1="112" />
            <wire x2="112" y1="272" y2="544" x1="112" />
            <wire x2="112" y1="544" y2="992" x1="112" />
            <wire x2="1296" y1="992" y2="992" x1="112" />
            <wire x2="112" y1="992" y2="1088" x1="112" />
            <wire x2="112" y1="1088" y2="2272" x1="112" />
            <wire x2="2112" y1="2272" y2="2272" x1="112" />
            <wire x2="1008" y1="1088" y2="1088" x1="112" />
            <wire x2="400" y1="544" y2="544" x1="112" />
            <wire x2="2112" y1="960" y2="960" x1="2096" />
            <wire x2="2112" y1="960" y2="2272" x1="2112" />
            <wire x2="2624" y1="960" y2="960" x1="2112" />
            <wire x2="2624" y1="960" y2="1088" x1="2624" />
            <wire x2="2896" y1="1088" y2="1088" x1="2624" />
            <wire x2="2432" y1="1088" y2="1728" x1="2432" />
            <wire x2="2448" y1="1728" y2="1728" x1="2432" />
            <wire x2="2624" y1="1088" y2="1088" x1="2432" />
            <wire x2="2896" y1="768" y2="768" x1="2624" />
            <wire x2="2624" y1="768" y2="960" x1="2624" />
        </branch>
        <branch name="XLXN_43">
            <wire x2="2848" y1="1760" y2="1760" x1="2704" />
            <wire x2="2848" y1="1760" y2="1920" x1="2848" />
            <wire x2="3008" y1="1920" y2="1920" x1="2848" />
            <wire x2="2848" y1="1632" y2="1760" x1="2848" />
            <wire x2="3008" y1="1632" y2="1632" x1="2848" />
        </branch>
        <branch name="RD">
            <wire x2="2112" y1="128" y2="128" x1="208" />
            <wire x2="2112" y1="128" y2="464" x1="2112" />
            <wire x2="2768" y1="464" y2="464" x1="2112" />
            <wire x2="2896" y1="464" y2="464" x1="2768" />
            <wire x2="3264" y1="464" y2="464" x1="2896" />
            <wire x2="2896" y1="464" y2="480" x1="2896" />
            <wire x2="208" y1="128" y2="336" x1="208" />
            <wire x2="448" y1="336" y2="336" x1="208" />
            <wire x2="208" y1="336" y2="464" x1="208" />
            <wire x2="880" y1="464" y2="464" x1="208" />
            <wire x2="208" y1="464" y2="1248" x1="208" />
            <wire x2="496" y1="1248" y2="1248" x1="208" />
            <wire x2="208" y1="1248" y2="1424" x1="208" />
            <wire x2="496" y1="1424" y2="1424" x1="208" />
            <wire x2="2112" y1="464" y2="464" x1="2096" />
            <wire x2="2832" y1="320" y2="320" x1="2768" />
            <wire x2="2768" y1="320" y2="464" x1="2768" />
            <wire x2="2896" y1="480" y2="480" x1="2816" />
            <wire x2="2816" y1="480" y2="896" x1="2816" />
            <wire x2="2896" y1="896" y2="896" x1="2816" />
            <wire x2="2816" y1="896" y2="1024" x1="2816" />
            <wire x2="2896" y1="1024" y2="1024" x1="2816" />
            <wire x2="2816" y1="1024" y2="1696" x1="2816" />
            <wire x2="2896" y1="1696" y2="1696" x1="2816" />
            <wire x2="3008" y1="1696" y2="1696" x1="2896" />
            <wire x2="2896" y1="1696" y2="1856" x1="2896" />
            <wire x2="3008" y1="1856" y2="1856" x1="2896" />
        </branch>
        <branch name="VD">
            <wire x2="3296" y1="1664" y2="1664" x1="3264" />
        </branch>
        <branch name="VI">
            <wire x2="3296" y1="1888" y2="1888" x1="3264" />
        </branch>
        <iomarker fontsize="28" x="3296" y="1664" name="VD" orien="R0" />
        <iomarker fontsize="28" x="3296" y="1888" name="VI" orien="R0" />
    </sheet>
</drawing>