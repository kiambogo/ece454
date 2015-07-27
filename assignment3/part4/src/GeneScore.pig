register TupToGeneBag.jar

samplesData = LOAD '$input' USING PigStorage(',');

samples = FOREACH samplesData GENERATE $0 AS SampleName, TOTUPLE($1 ..) AS Genes;

--dump samples;
--geneResults = FOREACH samples GENERATE TupToGeneBag(Genes);

dump geneResults;
