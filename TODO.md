# TODO

* ProcessAssertActivitiConfiguration: the engineservices could be made final, but this breaks some of the setup that is currently in place. Unclear whether this can be scrapped.

* Alleen unit tests voor modules zelf
* Alle integratietesten, dus met configuratie en proces uitvoeren (danwel Activiti, danwel Flowable) in 1 integrations module.
* Integrations module draait voor ale versies met een dependency naar process assertions versie ${profileVar} en bijbehorende Activiti versie ${profileVar}
* Suite voor integration verplaatsen naar nieuwe module
* Unit tests voor API classes en internals ook alleen daar opnemen en bv LogMessageProvider via suite testen (voorkomen altijd uitgevoerd)
* Integration common verdwijnt, java configuraties verplaatsen naar integration module

* Copyright texts in classes
* @author annotations in classes and XML files
