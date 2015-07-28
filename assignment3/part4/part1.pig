REGISTER Part1UDF.jar;
data = LOAD '$input' USING PigStorage(',');
genesCounts = FOREACH data GENERATE $0 as name, TOTUPLE($1..) AS nums;
genesMax = FOREACH genesCounts GENERATE $0, Part1UDF(nums);
STORE genesMax INTO '$output' USING PigStorage(',');
