<Qucs Schematic 0.0.19>
<Properties>
  <View=0,-214,800,675,1,0,0>
  <Grid=10,10,1>
  <DataSet=hw3question1.dat>
  <DataDisplay=hw3question1.dpl>
  <OpenDisplay=1>
  <Script=hw3question1.m>
  <RunScript=0>
  <showFrame=0>
  <FrameText0=Title>
  <FrameText1=Drawn By:>
  <FrameText2=Date:>
  <FrameText3=Revision:>
</Properties>
<Symbol>
</Symbol>
<Components>
  <DigiSource X 1 120 60 -35 16 0 0 "1" 1 "low" 0 "1ns; 1ns" 0 "1 V" 0>
  <DigiSource Z 1 120 240 -35 16 0 0 "3" 1 "low" 0 "1ns; 1ns" 0 "1 V" 0>
  <.Digi Digi1 1 90 -50 0 51 0 0 "TruthTable" 1 "10 ns" 0 "VHDL" 0>
  <DigiSource Y 1 120 170 -35 16 0 0 "2" 1 "low" 0 "1ns; 1ns" 0 "1 V" 0>
  <Inv Y1 1 200 170 -26 27 0 0 "1 V" 0 "0" 0 "10" 0 "old" 0>
  <AND Y3 1 290 210 -26 27 0 0 "2" 0 "1 V" 0 "0" 0 "10" 0 "old" 0>
  <OR Y2 1 390 100 -26 27 0 0 "2" 0 "1 V" 0 "0" 0 "10" 0 "old" 0>
  <GND * 1 490 100 0 0 0 0>
</Components>
<Wires>
  <120 220 120 240 "" 0 0 0 "">
  <120 220 260 220 "" 0 0 0 "">
  <120 170 170 170 "" 0 0 0 "">
  <230 170 230 200 "" 0 0 0 "">
  <230 200 260 200 "" 0 0 0 "">
  <320 110 320 210 "" 0 0 0 "">
  <320 110 360 110 "" 0 0 0 "">
  <120 60 120 90 "" 0 0 0 "">
  <120 90 360 90 "" 0 0 0 "">
  <420 100 490 100 "" 0 0 0 "">
</Wires>
<Diagrams>
</Diagrams>
<Paintings>
</Paintings>
