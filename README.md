# Local Place Names from Housing Advertisements

* Author: Yingjie Hu
* Email: yjhu.geo@gmail.com


### Overall description
This project provides some of the codes and data for harvesting local place names from housing advertisements. Particularly, the housing advertisements are posted on Craigslist, a local-oriented website. More details about this project can be found in the following paper:

Hu, Y., Mao, H. and McKenzie, G., 2018. A natural language processing and geospatial clustering framework for harvesting local place names from geotagged housing advertisements. International Journal of Geographical Information Science, pp.1-25.

Please feel free to re-use the code here for your own projects. If you use the code, we would appreciate if you could cite our paper. Thank you!

This study uses the geotagged housing advertisements in six different US cities. The figure below shows these cities and the general two-stage framework developed for harvesting local place names.
<p align="center">
<img align="center" src="https://github.com/YingjieHu/LocalPlaceName/blob/master/fig/LocalPlaceName.png" width="600" />
</p>


### Repository organization
The folder "ScaleStructureIdentification" provides the source code and the compiled version (in the "Release" folder) of the modified SSI algorithm. To use the compiled SSI, open a terminal and use the following command line:
  java -jar SSI.jar SSI_Input.csv 0.17
where "SSI_Input.csv" is the input file (which contains place names and associated coordinates) and "0.17" is the maximum distance of a study area in degrees (i.e., latitude and longitude degrees). The output includes the original SSI and three modified versions of SSI (see the paper).

The folder "Annotated Data" contains the 120 housing advertisements that are manually annotated by three human annotators. The folder "20PerCityTestData" contains the texts of the 120 housing advertisements for the six different cities in this study. The folder "HumanAnnotation" contains the annotated results from three annotators. The file "TestData_MergedAnnotation_GroundTruth.csv" is the combined result from the three annotators using majority vote.

The folder "DBpediaSpotlight" contains the source code of applying DBpedia Spotlight to housing advertisement data.

The folder "OpenCalais" contains the source code of applying OpenCalais to housing advertisement data.

The folder "libs" contains the necessary jar files to run the source code.





