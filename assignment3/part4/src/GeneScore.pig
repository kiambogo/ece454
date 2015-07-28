register TupToGeneBag.jar

samplesData = LOAD '$input' USING PigStorage(',');

samples = FOREACH samplesData GENERATE TupToGeneBag($0..) AS Genes:bag{t:tuple()};;

--dump samples;
geneResults = FOREACH samples GENERATE FLATTEN(Genes);

geneGrouped = GROUP geneResults BY $0;

scores = FOREACH geneGrouped GENERATE $0, AVG( (bag{tuple(double)})geneResults.$1);

STORE scores INTO '$output' USING PigStorage(',');


dump scores;
