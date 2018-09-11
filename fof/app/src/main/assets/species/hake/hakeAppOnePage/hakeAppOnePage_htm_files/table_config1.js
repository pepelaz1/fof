var xaraSwidgets_table1Templates = {


    main:	'<div id="{component_id}OuterDiv" >'
        +	'<table id="{component_id}_table" class="table tablesorter">'
        +	'</table>'
        +	'</div>'
};





// this is the constructor for a component
// it loops through each 'entry' in the array of data and compiles the entry template for it
// it then applies the resulting HTML to the main template before writing the whole lot to the div on the page
// it then initialises the actual jquery plugin for the div (that now contains the required HTML as a result of writing the template to it)
function xaraSwidgets_table1Constructor(divID, data)
{

    myTheme = (data[0].theme);
    myData = (data[0].csv);
    myRows = (data[0].rows);
    imgFirst = (data[0].first);
    imgLast = (data[0].last);
    imgNext = (data[0].next);
    imgPrev = (data[0].prev);
    imgFirstDark = (data[0].firstdark);
    imgLastDark = (data[0].lastdark);
    imgNextDark = (data[0].nextdark);
    imgPrevDark = (data[0].prevdark);


    // loop through each entry in the array and compile the entry template for it
    for(var i=1; i<data.length; i++)
    {
    }
    var com1_id=divID;
//	entryHTML = xsw_ea_htmlbr(entryHTML);
    // now lets compile the 'main' template which acts as a wrapper for each entry




    var mainData = {
        component_id:divID,
        com_id:com1_id
    };



    var mainTemplate = xaraSwidgets_compileTemplate(xaraSwidgets_table1Templates.main, mainData);





    // find the theme value to determine whether theme colors should be matched.

    var defaultTheme = 0;
    var enteredTheme = parseInt(myTheme);
    var theme = isNaN(enteredTheme) ? defaultTheme : enteredTheme;
//		var theme = parseInt(myTheme);
    if(!isNaN(theme))
    {
        useTheme = theme;
    }
    if (theme ==1){
        var $p = $("<p class='xr_c_Theme_Color_1'></p>").hide().appendTo("body");

    }
    else if (theme ==0){
        var $p = $("<p class='xr_c_Table1_Color_1'></p>").hide().appendTo("body");

    }

    var enteredovercolor = $p.css("color");
    var defaultovercolor = '#999';
    //	var overcolor = isNaN(enteredovercolor) ? defaultovercolor : enteredovercolor

    if (enteredovercolor !== 'rgb(0, 0, 0)')
    {
        var overcolor= enteredovercolor
    }
    else
    {
        var overcolor= defaultovercolor;
    }

    $p.remove();
  //  console.log(overcolor)
    

    // get the row value
    var enteredRows = parseInt(myRows);
    var defaultRows = '3';
    var myRows = isNaN(enteredRows) ? defaultRows : enteredRows;

    // now lets apply the resulting HTML for the whole component to the main DIV that was exported by XARA

    $('#' + divID).html(mainTemplate);


    // get the dimensions of the parent div

    var width = $('#' + divID).parent('div').width();
    var height = $('#' + divID).parent('div').height();
    $('#' + divID).css('width',width);
    $('#' + divID).css('height',height);

    function trim(stringToTrim) {
        return stringToTrim.replace(/^\s+|\s+$/g,"");  //removes spaces
    }

    var csv = trim(myData);//trim the string to be used for the csv data
  
    function datasort(options,csvData){
 
    //      $.get('data.csv', function(data) {
                // Split the lines
                var lines = csvData.split('\n');
                $.each(lines, function(lineNo, line) {
                    var items = line.split(',');
                    
                    // header line containes categories
                    if (lineNo == 0) {
                        $.each(items, function(itemNo, item) {
                            if (itemNo >= 0) options.xAxis.categories.push(item);
                        });
                    }
                    
                    // the rest of the lines contain data with their name in the first position
                    else {
                        var series = { 
                            data: []
                        };
                        $.each(items, function(itemNo, item) {
                            if (itemNo == 0) {
                               // series.name = item;
                                series.data.push(item);
                            } else {
                                series.data.push(item);
                            }
                        });
                        
                        return options.series.push(series);

                    }
                    
                });
    };      

    var options = {
                xAxis: {
                    categories: []
                },  

                series: []
            };
            
     datasort(options,csv);  // sort the data

    var header = options.xAxis;
    for(key in header) {
        var value = header[key];
        //do whatever you want with the data, such as:
 //           console.log("The value of ", key, " is ", value);
//			console.log("<tr>");
        $('<tr class="'+divID+'row'+key+'">').appendTo('#' +divID+ '_table');
        $.each(value, function(index, item) {

                $("<th>"+item+"</th>").appendTo('.'+divID+'row'+key);

           
        });
    }

    var body = options.series;
    for(key in body) {
        var value = body[key];
        for (key1 in value){
            var value1 = value[key1];
            
            $('<tr class="'+divID+'row'+key+'">').appendTo('#' +divID+ '_table');
        $.each(value1, function(index, item) {
                $("<td>"+item+"</td>").appendTo('.'+divID+'row'+key);
       
        });    
        }
        
    }


    $('#' +divID+ '_table').find("tr:first").before('<thead id="'+divID+'_head">');
    $('#' +divID+ '_table').find("tr:first").appendTo('#' +divID+ '_head');
    $('#' +divID+ '_head').appendTo('#' +divID+ '_table');
    $('#' +divID+ '_table').find("tbody").appendTo('#' +divID+ '_table');
    $('#' +divID+ '_table').find("tr:first").css('background',overcolor);

    

    /*
    find out if the overcolor is returned as hsv or rgb.
    

    */
//console.log(overcolor)
// funcion found from http://stackoverflow.com/questions/4262417/jquery-hex-to-rgb-calculation-different-between-browsers
function hex2rgb(hexStr){
    // note: hexStr should be #rrggbb
    var hex = parseInt(hexStr.substring(1), 16);
    var r = (hex & 0xff0000) >> 16;
    var g = (hex & 0x00ff00) >> 8;
    var b = hex & 0x0000ff;
    return [r, g, b];
}
  
  var rgb;  
  if(overcolor.substring(0, 3) == 'rgb') {
//    console.log('im rgb')
   var rgb = overcolor.match(/\d+/g);

} else { // it's a hex
 //    console.log('im hex')
   var rgb = hex2rgb(overcolor);

}
 

    /*
    Decide whether to use black or white graphics and text depending on the overcolor.
    lets addup the 3 rgb values and then divide by 3.  If the value is larger than 
    128 lets apply black text and graphics, otherwise leave as is

    */
//  console.log(rgb)
    


    
   
        var total = 0;
            for(var i = 0; i < rgb.length; i++){
           //     console.log(i)
                if (i==0) {
                var thisVal = parseInt(rgb[0])*0.21;
       //         console.log(thisVal)
                          } 
                if (i==1) {
                var thisVal = parseInt(rgb[0])*0.71;
     //           console.log(thisVal)
                          }

                 if (i==2) {
                var thisVal = parseInt(rgb[0])*0.07;
        //        console.log(thisVal)
                          }                         

                    if(!isNaN(thisVal)){
                        total += thisVal;
                    }
                }
 //   console.log(total)
    shade = total;
   // console.log(shade)
    
//   0.21 R + 0.71 G + 0.07 B.


    /*
    set the header graphics options depending on the shade value
    */
    if (shade <128)
    {

     
            /*
     Lets add some paging now.  let the user decide how many rows to display.
     If there are more rows than displayed, then display pager, otherwise why bother?
     */
    var pager = '<div id="'+divID+'_pager" class="pager">'
        +   '<form>'
        +   '<div style="float:left; margin:2px 0 0 0;"><img src="'+imgFirst+'" class="first"/></div>'
        +   '<div style="float:left; margin:2px 0 0 0;"><img src="'+imgPrev+'" class="prev"/></div>'
        +   '<div style="float:left;margin: 0px auto;"><input type="text" readonly class="pagedisplay"/></div>'
        +   '<div style="float:left; margin:2px 0 0 0;"><img src="'+imgNext+'" class="next"/></div>'
        +   '<div style="float:left; margin:2px 0 0 0;"><img src="'+imgLast+'" class="last"/></div>'
        +   '<input type="hidden" class="pagesize" value="'+myRows+'"></form>'
        +   '</div>';


        $('#' +divID+ '_head').find("th").css({'color':'#FFFFFF' });  

     //     console.log('HERE')
    $('#' +divID+ '_table').tablesorter({
          cssAsc: "headerSortUp1",
          cssDesc: "headerSortDown1",
          cssHeader: "header1"

    });


       
    }
            
    else 
    {

                 /*
     Lets add some paging now.  let the user decide how many rows to display.
     If there are more rows than displayed, then display pager, otherwise why bother?
     */
    var pager = '<div id="'+divID+'_pager" class="pager">'
        +   '<form>'
        +   '<div style="float:left; margin-top:4px ;"><img src="'+imgFirstDark+'" class="first"/></div>'
        +   '<div style="float:left; margin-top:4px ;"><img src="'+imgPrevDark+'" class="prev"/></div>'
        +   '<div style="float:left;margin: 0px auto;"><input type="text" readonly class="pagedisplay"/></div>'
        +   '<div style="float:left; margin-top:4px ;"><img src="'+imgNextDark+'" class="next"/></div>'
        +   '<div style="float:left; margin-top:4px ;"><img src="'+imgLastDark+'" class="last"/></div>'
        +   '<input type="hidden" class="pagesize" value="'+myRows+'"></form>'
        +   '</div>';

     
        //  console.log(pager)
        $('#' +divID+ '_table').tablesorter({
        
        });

    }               


  //  console.log(options.series.length)

    if (options.series.length > myRows)
    {

        $('head').append("<style>#" + divID +"_pager {-moz-border-radius: 4px; -webkit-border-radius: 4px; border-radius: 4px; font-size:12px; padding:2px 2px; background:"+overcolor+";   }"
        +"#" + divID +"_pager .pagedisplay {width: 50px; padding-top:4px; border:1px solid #cccccc;  text-align:center;  -moz-border-radius: 2px; -webkit-border-radius: 2px; border-radius: 2px;    }"
          +"#" + divID +"_pager img {  opacity: 0.8; /* For IE 5-7 */ filter: progid:DXImageTransform.Microsoft.Alpha(Opacity=80);    /* For IE 8 */-MS-filter: 'progid:DXImageTransform.Microsoft.Alpha(Opacity=80)';  -moz-border-radius: 2px; -webkit-border-radius: 2px; border-radius: 2px;    margin:0 1px; padding:0px 7px;  }"
 
        +"</style>" );


        $(pager).appendTo('#' +divID+ 'OuterDiv');

        $('#' +divID+ '_table').tablesorterPager({
            container: $('#' +divID+ '_pager')
          
        });

    }
    var sorting = 0;
    // sort on the first column
    $('#' +divID+ '_table').trigger("sorton",[sorting]);
    // invoke the effect
    $('#' +divID+ '_pager').css('top','');
// write the css values to the doc





}



