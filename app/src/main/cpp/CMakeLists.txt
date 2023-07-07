cmake_minimum_required(VERSION 3.22.1)

project("graphtestforandroid")

add_library(
        graphtestforandroid
        SHARED
        native-lib.cpp)

find_library(
        log-lib
        log)

find_library(jnigraphics-lib jnigraphics)


target_link_libraries(
        graphtestforandroid
        ${log-lib}
        ${jnigraphics-lib})
