# Local Place Names from Geotagged Housing Posts

* Author: Yingjie Hu
* Email: yjhu.geo@gmail.com


### Overall description
This project provides some of the codes for extracting local place names from geotagged housing posts. The housing advertisements are posted on Craigslist, a local-oriented website. More details about this project can be found in the following paper:

Hu, Y., Mao, H. and McKenzie, G., 2018. A natural language processing and geospatial clustering framework for harvesting local place names from geotagged housing advertisements. International Journal of Geographical Information Science, 33(4), 714-738. http://www.acsu.buffalo.edu/~yhu42/papers/2018_IJGIS_LocalName.pdf

Please feel free to re-use the code here for your own projects. If you use the code or data, we would appreciate if you could cite our paper above. Thank you!

This study uses the geotagged housing advertisements in six different US cities. The figure below shows these cities and the general two-stage framework developed for harvesting local place names.
<p align="center">
<img align="center" src="https://github.com/YingjieHu/LocalPlaceName/blob/master/fig/LocalPlaceName.png" width="600" />
</p>


### Repository organization
* The folder "ScaleStructureIdentification" provides the source code and the compiled version (in the "Release" folder) of the modified SSI algorithm. To use the compiled SSI, open a terminal and use the following command line:
  "java -jar SSI.jar SSI_Input.csv 0.17"
where "SSI_Input.csv" is the input file (which contains place names and their associated coordinates) and "0.17" is the maximum distance of a study area in degrees (i.e., latitude and longitude degree). The output includes the ranked place names and four versions (one original and three modified) of SSI based on their coordinates (see the paper).

* The folder "DBpediaSpotlight" contains the source code of applying DBpedia Spotlight to housing advertisement data. You may need to install your own DBpedia Spotlight, and you can find more details here: https://github.com/dbpedia-spotlight/dbpedia-spotlight/wiki/Run-from-a-JAR

* The folder "OpenCalais" contains the source code of using the OpenCalais API to extract place names from housing advertisement texts.

* The folder "libs" contains the necessary jar files in order to configure and run the source code of this project.

* The folder "Data" contains 120 randomly selected housing advertisements (with 20 from each of the six cities) used for testing the performance of our method. This folder also contains the ground-truth annotations from three different human annotators.




