function xr_png(){var vs = navigator.appVersion.split("MSIE");var v = parseFloat(vs[1]);if ((v >= 5.5) && (document.body.filters)){xr_xr.style.display="none";for(var i=0;i<document.images.length;i++){var img = document.images[i];if(img.className=="xr_ap"&&(img.width>1 || img.height<404)){var iN = img.src.toUpperCase();if (iN.substring(iN.length-3, iN.length) == "PNG"){var iT = "title='"+img.title+"' alt='"+img.alt+"' ";if (img.id) iT = iT+"id='"+img.id+"' ";var iS = "display:inline-block; position:absolute; border:none;"+img.style.cssText;if (img.parentElement.href) iS = "cursor:hand;"+iS;var re=/\{[\s\S]*?\}/;var xx=" "+img.onmousemove;if(xx.match(re)) iT=iT+' onmousemove="'+xx.match(re)+'"';xx=" "+img.onmouseover;if(xx.match(re)) iT=iT+' onmouseover="'+xx.match(re)+'"';xx=" "+img.onmouseout;if(xx.match(re)) iT=iT+' onmouseout="'+xx.match(re)+'"';xx=" "+img.onclick;if(xx.match(re)) iT=iT+' onclick="'+xx.match(re)+'"';var r = "<img " + iT+"src=\"glossaryNew_htm_files/0.gif\" style=\"" + iS + ";"+ "filter:progid:DXImageTransform.Microsoft.AlphaImageLoader"+ "(src=\'" + img.src + "\', sizingMethod='image');\"></img>";img.outerHTML = r;};};};xr_xr.style.display="block";};};xr_png();
