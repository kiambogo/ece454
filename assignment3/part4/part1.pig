data = load 'input/Test_Cases/1k_Samples/1.txt' using PigStorage(',') AS (name:chararray, nums:bag{double});
max_genes = FOREACH data GENERATE data.name, MAX(data.nums);
dump max_genes;
