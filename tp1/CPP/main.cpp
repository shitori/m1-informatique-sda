#include<iostream>
#include<time.h>
#include<cstdlib>	
#include "arraylist.hpp"
#include "analyzer.hpp"


int main(int argc, char ** argv){
  int i;
  // Tableau dynamique.
  ArrayList<int> a;
  // Analyse du temps pris par les opérations.
  Analyzer time_analysis;
  // Analyse du nombre de copies faites par les opérations.
  Analyzer copy_analysis;
  // Analyse de l'espace mémoire inutilisé.
  Analyzer memory_analysis;
  struct timespec before, after;
  // Booléen permettant de savoir si une allocation a été effectuée.
  bool memory_allocation;

  for(i = 0; i < 1000000 ; i++){
    // Ajout d'un élément et mesure du temps pris par l'opération.
    timespec_get(&before, TIME_UTC);
    memory_allocation = a.append(i);
    timespec_get(&after, TIME_UTC);

    // Enregistrement du temps pris par l'opération
    time_analysis.append(after.tv_nsec - before.tv_nsec);
    // Enregistrement du nombre de copies efféctuées par l'opération.
    // S'il y a eu réallocation de mémoire, il a fallu recopier tout le tableau.
    copy_analysis.append( (memory_allocation)? i:1 );
    // Enregistrement de l'espace mémoire non-utilisé.
    memory_analysis.append( a.capacity() - a.size() );
  }

  // Affichage de quelques statistiques sur l'expérience.
  std::cerr<<"Total cost :"<<time_analysis.get_total_cost()<<std::endl;
  std::cerr<<"Average cost :"<<time_analysis.get_average_cost()<<std::endl;
  std::cerr<<"Variance :"<<time_analysis.get_variance()<<std::endl;
  std::cerr<<"Standard deviation :"<<time_analysis.get_standard_deviation()<<std::endl;    

  // Sauvegarde les données de l'expérience.
  time_analysis.save_values("../plots/dynamic_array_time_cpp.plot");
  copy_analysis.save_values("../plots/dynamic_array_copy_cpp.plot");
  memory_analysis.save_values("../plots/dynamic_array_memory_cpp.plot");
  
  return 0;
}
