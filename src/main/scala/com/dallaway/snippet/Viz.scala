package com.dallaway.snippet

import scala.xml.NodeSeq
import net.liftweb.util.Helpers._
import net.liftweb.http.js.JsCmds._
import net.liftweb.http.js._
import net.liftweb.widgets.flot._
import net.liftweb.common._
import JE._


class Viz {

  val data = List(50,80) // range 0-100
  val bar_labels = List("Fred", "Bert")
  val generatedValue = new java.util.Random nextInt // generates an arbitary value
  val flagValue = generatedValue % 2 // Zero or One

  // -- GOOGLE CHARTS EXAMPLE: ---------------------------------------------

  val width = 600
  val height = 225

def googleOMeter(data: Int, bar_label: String) = "http://chart.apis.google.com/chart?" + List(
    "chtt=Meter+Reading+for+@" + bar_label,
    "chts=000000,10",
    "chxt=x,y",
    "chxl=0:|Low|High",
    "chxt=y",
    "chs=300x150",
    "cht=gm",
    "chd=t:" + data,
    //    "chdl=" + bar_label,
    "chl=data").mkString("&") // returns google o meter chart

  def googleUrl = "http://chart.apis.google.com/chart?" + List(
        "chxt=x,y", 
        "chxl=0:|" + bar_labels.mkString("|"), 
        "chs=%dx%d".format(width,height), 
        "cht=bvg", 
        "chco=A2C180", 
        "chd=t:"+data.mkString(",") ).mkString("&")

  def google(xhtml: NodeSeq) = <img src={googleUrl} width={width} height={height} alt="graph"/>

 def googleOMeterChart(xhtml: NodeSeq) = <img src={googleOMeter(data(flagValue), bar_labels(flagValue))} width={width} height={height} alt="graph"/>

                                                 

  // -- FLOT EXAMPLE: -----------------------------------------------------

  def flot(xhtml: NodeSeq) = {
    
    // One FlotSerie for each bar
    val data_to_plot = for ( (y,x) <- data zipWithIndex ) yield new FlotSerie() {
        override val data : List[(Double,Double)] = (x.toDouble, y.toDouble) :: Nil
        override val label = Full( bar_labels(x) ) 
    } 

    val options:FlotOptions = new FlotOptions () {
        override val series = Full( Map( "bars" -> JsObj("show"->true, "barWidth"->0.5) ) )
       
        override val xaxis = Full( new FlotAxisOptions() {
            override def min = Some(0d)
            override def max = Some(data.length * 1d)
        })
        
        override def grid = Full( new FlotGridOptions() {
        	override def hoverable = Full(true)
        })
    }
  
    Flot.render("graph_area", data_to_plot, options, Flot.script(xhtml))
  }
}