<Qucs Schematic 0.0.19>
<Properties>
  <View=0,-59,800,792,1,0,14>
  <Grid=10,10,1>
  <DataSet=fulladder.dat>
  <DataDisplay=fulladder.dpl>
  <OpenDisplay=1>
  <Script=fulladder.m>
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
  <fa1b Y1 1 210 110 -31 58 0 0 "6" 0 "1 ns" 0>
  <DigiSource A 1 80 80 -35 16 0 0 "1" 1 "low" 0 "1ns; 1ns" 0 "1 V" 0>
  <DigiSource B 1 80 140 -35 16 0 0 "2" 1 "low" 0 "1ns; 1ns" 0 "1 V" 0>
  <DigiSource CarryIn 1 80 210 -35 16 0 0 "3" 1 "low" 0 "1ns; 1ns" 0 "1 V" 0>
  <.Digi fulladder 1 80 -30 0 51 0 0 "TruthTable" 1 "10 ns" 0 "VHDL" 0>
</Components>
<Wires>
  <80 80 80 100 "" 0 0 0 "">
  <80 100 160 100 "" 0 0 0 "">
  <80 120 80 140 "" 0 0 0 "">
  <80 120 160 120 "" 0 0 0 "">
  <80 210 120 210 "" 0 0 0 "">
  <120 140 120 210 "" 0 0 0 "">
  <120 140 160 140 "" 0 0 0 "">
  <260 80 260 100 "" 0 0 0 "">
  <260 80 340 80 "" 0 0 0 "">
  <260 120 260 150 "" 0 0 0 "">
  <260 150 340 150 "" 0 0 0 "">
  <340 80 340 80 "Sum" 370 50 0 "">
  <340 150 340 150 "CarryOut" 370 120 0 "">
</Wires>
<Diagrams>
</Diagrams>
<Paintings>
</Paintings>
