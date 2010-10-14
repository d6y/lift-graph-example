package com.dallaway.snippet

import scala.xml.NodeSeq
import net.liftweb.util.Helpers._
import net.liftweb.http.js.JsCmds._
import net.liftweb.http.js._
import net.liftweb.widgets.flot._
import net.liftweb.common._
import JE._


class Viz {

  // -- GOOGLE CHARTS EXAMPLE: ---------------------------------------------

  val width = 300
  val height = 225

  val data = List(50,80) // range 0-100
  val bar_labels = List("Fred", "Bert")

  def googleUrl = "http://chart.apis.google.com/chart?" + List(
        "chxt=x,y", 
        "chxl=0:|" + bar_labels.mkString("|"), 
        "chs=%dx%d".format(width,height), 
        "cht=bvg", 
        "chco=A2C180", 
        "chd=t:"+data.mkString(",") ).mkString("&")

  def google(xhtml: NodeSeq) = <img src={googleUrl} width={width} height={height} alt="graph"/>
                                                 

  // -- FLOT EXAMPLE: -----------------------------------------------------

  def flot(xhtml: NodeSeq) = {
    
    val data_values: List[(Double,Double)] = List( (10d, 42d)  )

    val data_to_plot = new FlotSerie() {
        override val data = data_values
        override val label = Full("hi")
    } 

    val options:FlotOptions = new FlotOptions () {
        override val series = Full( Map( "bars" -> JsObj("show"->true, "barWidth"->1) ) )
       
        override val xaxis = Full( new FlotAxisOptions() {
            override def min = Some(1d)
            override def max = Some(20d)
        })
    }

    Flot.render ( "graph_area", List(data_to_plot), options, Flot.script(xhtml))
  }
}