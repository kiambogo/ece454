register DotProduct.jar;
samples = LOAD '$input' USING PigStorage(',');
sample1 = FOREACH samples GENERATE $0, TOTUPLE($1..) as genes;
sample2 = FOREACH samples GENERATE $0, TOTUPLE($1..) as genes;
crossSamples = CROSS sample1,sample2;
filteredCross = FILTER crossSamples BY $0 < $2;
similarities = FOREACH filteredCross GENERATE DotProduct($0..);
STORE similarities INTO '$output' USING PigStorage(',');