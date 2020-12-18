<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml"
      xmlns:dc="http://purl.org/dc/elements/1.1/" >
      
<head>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script><script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
<script type="text/javascript" src="https://cdn.datatables.net/1.10.20/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/1.10.20/js/dataTables.bootstrap.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/responsive/2.2.3/js/dataTables.responsive.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/responsive/2.2.3/js/responsive.bootstrap.min.js"></script>


<script type="text/javascript">
   var bicycleStationsTable,hospitalsTable,busTable,tramsTable,metroTable,sncfTable;
   $(function () {
       $("#bicycleStations").hide();
       $("#hospitals").hide();
       $("#sncfStations").hide();
       $("#metroStations").hide();
       $("#busStations").hide();
       $("#tramStations").hide();
         bicycleStationsTable = $('#bicycleStationsTable').DataTable({
                    "bDestroy": true,
                    'bAutoWidth': false,
                    'searching': false,
                    'info': false,
                    "columnDefs": [
                        {width: "320px", "targets": [0]},
                        {width: "100px", "targets": [1]},
                        {width: "80px", "targets": [2]},
                        {width: "100px", "targets": [3]},
                        {className: "text-center", "targets": [1, 2, 3]},
                        {className: "action-btn-column", "targets": [4]},
                        {"visible": false, "targets":[7]}
                    ],
                    "order": [
                        [0, "asc"]
                    ],
                    responsive: true
                });
         
              hospitalsTable = $('#hospitalsTable').DataTable({
                          "sPaginationType": "full_numbers",
                          "bDestroy": true,
                          'bAutoWidth': false,
                          'searching': false,
                          'info': false,
                          'paging': false,
                          "columnDefs": [
                              {width: "100px", "targets": [0,1,4]},                         
                              {width: "80px", "targets": [2]},
                              {width: "320px", "targets": [3]},
                              {className: "text-center", "targets": [1, 2, 3]}                             
                          ],
                          "order": [
                              [0, "asc"]
                          ],
                          responsive: true
                      });
               
                    busTable = $('#busTable').DataTable({
                                "sPaginationType": "full_numbers",
                                "bDestroy": true,
                                'bAutoWidth': false,
                                'searching': false,
                                'info': false,
                                'paging': false,
                                "columnDefs": [
                                    {width: "320px", "targets": [0]},
                                    {width: "100px", "targets": [1,3]},
                                    {width: "80px", "targets": [2]},
                                    {className: "text-center", "targets": [1, 2, 3]}                                   
                                ],
                                "order": [
                                    [0, "asc"]
                                ],
                                responsive: true
                            });
                     
                          tramsTable = $('#tramsTable').DataTable({
                                      "sPaginationType": "full_numbers",
                                      "bDestroy": true,
                                      'bAutoWidth': false,
                                      'searching': false,
                                      'info': false,
                                      'paging': false,
                                      "columnDefs": [
                                    	  {width: "320px", "targets": [0]},
                                          {width: "100px", "targets": [1,3]},
                                          {width: "80px", "targets": [2]},
                                          {className: "text-center", "targets": [1, 2, 3]}
                                      ],
                                      "order": [
                                          [0, "asc"]
                                      ],
                                      responsive: true
                                  });
                         
                               
                               metroTable = $('#metroTable').DataTable({
                                           "sPaginationType": "full_numbers",
                                           "bDestroy": true,
                                           'bAutoWidth': false,
                                           'searching': false,
                                           'info': false,
                                           'paging': false,
                                           "columnDefs": [
                                         	  {width: "320px", "targets": [0]},
                                               {width: "100px", "targets": [1,3]},
                                               {width: "80px", "targets": [2]},
                                               {className: "text-center", "targets": [1, 2, 3]}
                                           ],
                                           "order": [
                                               [0, "asc"]
                                           ],
                                           responsive: true
                                       });
                            
                                    sncfTable = $('#sncfTable').DataTable({
                                                "sPaginationType": "full_numbers",
                                                "bDestroy": true,
                                                'bAutoWidth': false,
                                                'searching': false,
                                                'info': false,
                                                'paging': false,
                                                "columnDefs": [
                                                    {width: "320px", "targets": [0]},
                                                    {width: "100px", "targets": [1,5,6]},
                                                    {width: "80px", "targets": [2]},
                                                    {width: "110px", "targets": [3,4]},                                                  
                                                    {className: "text-center", "targets": [1, 2, 3, 5, 6]}
                                                    
                                                ],
                                                "order": [
                                                    [0, "asc"]
                                                ],
                                                responsive: true
                                            });


        $ ("#search-form").submit(function(event){
           // stop submit the form, we will post it manually.
            event.preventDefault();
            $(".error").remove();
            if($("#choice").val() =="HOSPITALS"){
                hospitals();
            }
            else if($("#choice").val() =="BUSSTATIONS"){
                btm("BUSSTATIONS");
            }
            else if($("#choice").val() =="TRAMSTATIONS"){
            	 btm("TRAMSTATIONS");
            } 
            else if($("#choice").val() =="BICYCLESTATIONS"){
                bicycle();
            } else if($("#choice").val() =="SNCFSTATIONS"){
                sncf();
            }
            else if($("#choice").val() =="METROSTATIONS"){
            	 btm("METROSTATIONS");
            }else{
                $('#choice').after('<span class="error">This field is required</span>');
            }
        });
    });

//function searchByChoice ()
function bicycle (){
    $("#bicycleStations").show();
    //$('#loadingModal').modal('show');
    var choice = $("#choice").val();
    $("#btn-search").prop("disabled", true);
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/api/bicyclesearch/",
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            setBicycleTableRows(data);
            $("#btn-search").prop("disabled", false);
            //$('#loadingModal').modal('hide');
        },
        error: function (e) {
            var json = "<h4>Ajax Response</h4><pre>"+ e.responseText + "</pre>";
            $('#error-msg').innerHTML=json;
            console.log("ERROR : ", e);
            $("#btn-search").prop("disabled", false);
        }
    });
}

// TODO take RDFa values from the data store, dynamically
// not hardcoding.
// TODO check value.ID is not working.
// TODO add content tag for date field
// TODO xsd date time for lat, long
function setBicycleTableRows(data){
    var res='';
    $.each (data, function (key, value) {
   // console.log(value);
        res +=
        '<tr>'+
            '<td vocab="http://Schema.org/" resource="'+value.name+'" typeof="Station"><span property="dc:title">'+value.name+'</span></td>'+
            '<td resource="'+value.name+'"><span property="geo:lat">'+value.lat+'</span></td>'+
            '<td  resource="'+value.name+'"><span property="geo:lon">'+value.lon+'</span></td>'+
            '<td  resource="'+value.name+'"><span property="onto:capacity">'+value.capacity+'</span></td>'+


        '</tr>';
    });

    $("#hospitals").hide();
    $("#sncfStations").hide();
    $("#metroStations").hide();
    $("#busStations").hide();
    $("#tramStations").hide();
    
    $('#bicycletbody').html(res);
}
function hospitals(){
    $("#hospitals").show();
    //$('#loadingModal').modal('show');
    var choice = $("#choice").val();
    $("#btn-search").prop("disabled", true);
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/api/hospitalsearch/",
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
        	setHospitalTableRows(data);
            $("#btn-search").prop("disabled", false);
            //$('#loadingModal').modal('hide');
        },
        error: function (e) {
            var json = "<h4>Ajax Response</h4><pre>"+ e.responseText + "</pre>";
            $('#error-msg').innerHTML=json;
            console.log("ERROR : ", e);
            $("#btn-search").prop("disabled", false);
        }
    });
}
function setHospitalTableRows(data){
    var res='';
    $.each (data, function (key, value) {
   // console.log(value);
        res +=
        '<tr>'+
            '<td vocab="https://opendata.paris.fr/api/records/1.0/search/?dataset=velib-emplacement-des-stations" resource="'+value.id+'" typeof="PublicBicycleStation"><span property="hospitalcategory">'+value.catagorie+'</span></td>'+
            '<td  resource="'+value.id+'"><span property="lat">'+value.lat+'</span></td>'+
            '<td  resource="'+value.id+'"><span property="lon">'+value.lon+'</span></td>'+
            '<td  resource="'+value.id+'"><span property="capacity">'+value.adresse+'</span></td>'+
            '<td  resource="'+value.id+'"><span property="availableBikes">'+value.tel_number+'</span></td>'+
        '</tr>';
    });

    $("#bicycleStations").hide();
    $("#sncfStations").hide();
    $("#metroStations").hide();
    $("#busStations").hide();
    $("#tramStations").hide();
    
    $('#hospitaltbody').html(res);
}
function  sncf(){
    $("#sncfStations").show();
    //$('#loadingModal').modal('show');
    var choice = $("#choice").val();
    $("#btn-search").prop("disabled", true);
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/api/sncfsearch/",
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
        	setSNCFTableRows(data);
            $("#btn-search").prop("disabled", false);
            //$('#loadingModal').modal('hide');
        },
        error: function (e) {
            var json = "<h4>Ajax Response</h4><pre>"+ e.responseText + "</pre>";
            $('#error-msg').innerHTML=json;
            console.log("ERROR : ", e);
            $("#btn-search").prop("disabled", false);
        }
    });
}
function setSNCFTableRows(data){
    var res='';
    $.each (data, function (key, value) {
   // console.log(value);
        res +=
        '<tr>'+
            '<td vocab="https://opendata.paris.fr/api/records/1.0/search/?dataset=velib-emplacement-des-stations" resource="'+value.id+'" typeof="PublicBicycleStation"><span property="stationName">'+value.SNCFStationName+'</span></td>'+
            '<td  resource="'+value.id+'"><span property="geo:lat">'+value.lat+'</span></td>'+
            '<td  resource="'+value.id+'"><span property="geo:lon">'+value.lon+'</span></td>'+
            '<td  resource="'+value.id+'"><span property="onto:hasArrival">'+value.ArrivalTime+'</span></td>'+
            '<td  resource="'+value.id+'"><span property="onto:hasDeparture">'+value.DepartTime+'</span></td>'+
            '<td  resource="'+value.id+'"><span property="onto:hasAscenseur">'+value.HasElevatorr+'</span></td>'+
            '<td  resource="'+value.id+'"><span property="onto:hasEscalator">'+value.HasEscalator+'</span></td>'+
        '</tr>';
    });

    $("#bicycleStations").hide();
    $("#hospitals").hide();
    $("#metroStations").hide();
    $("#busStations").hide();
    $("#tramStations").hide();
    
    $('#sncftbody').html(res);
}
function btm(type){
	if(type=="BUSSTATIONS"){
		 $("#busStations").show();
	}
	else if(type=="TRAMSTATIONS"){
		 $("#tramStations").show();
	}
	else if(type=="METROSTATIONS"){
		 $("#metroStations").show();
	}
    //$('#loadingModal').modal('show');
    var choice = $("#choice").val();
    $("#btn-search").prop("disabled", true);
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/api/btmsearch/"+choice,
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
        	setBTMTableRows(choice,data);
            $("#btn-search").prop("disabled", false);
            //$('#loadingModal').modal('hide');
        },
        error: function (e) {
            var json = "<h4>Ajax Response</h4><pre>"+ e.responseText + "</pre>";
            $('#error-msg').innerHTML=json;
            console.log("ERROR : ", e);
            $("#btn-search").prop("disabled", false);
        }
    });
}
function setBTMTableRows(choice,data){
    var res='';
    if(choice=="BUSSTATIONS"){
    	 $.each (data, function (key, value) {
    		   // console.log(value);
    		        res +=
    		        '<tr>'+
    		            '<td vocab="https://opendata.paris.fr/api/records/1.0/search/?dataset=velib-emplacement-des-stations" resource="'+value.id+'" typeof="PublicBicycleStation"><span property="stationName">'+value.name+'</span></td>'+
    		            '<td  resource="'+value.id+'"><span property="geo:lat">'+value.lat+'</span></td>'+
    		            '<td  resource="'+value.id+'"><span property="geo:lon">'+value.lon+'</span></td>'+
    		            '<td  resource="'+value.id+'"><span property="onto:busStation">'+value.number+'</span></td>'+
    		        '</tr>';
    		    });

         $("#bicycleStations").hide();
         $("#hospitals").hide();
         $("#sncfStations").hide();
         $("#metroStations").hide();
         $("#tramStations").hide();
         
    		    $('#bustbody').html(res);
	}
	else if(choice=="TRAMSTATIONS"){
		 $.each (data, function (key, value) {
			   // console.log(value);
			        res +=
			        '<tr>'+
			            '<td vocab="https://opendata.paris.fr/api/records/1.0/search/?dataset=velib-emplacement-des-stations" resource="'+value.id+'" typeof="PublicBicycleStation"><span property="stationName">'+value.name+'</span></td>'+
			            '<td  resource="'+value.id+'"><span property="geo:lat">'+value.lat+'</span></td>'+
			            '<td  resource="'+value.id+'"><span property="geo:lon">'+value.lon+'</span></td>'+
			            '<td  resource="'+value.id+'"><span property="onto:tramNumber">'+value.number+'</span></td>'+
			        '</tr>';
			    });

	       $("#bicycleStations").hide();
	       $("#hospitals").hide();
	       $("#sncfStations").hide();
	       $("#metroStations").hide();
	       $("#busStations").hide();
	       
			    $('#tramtbody').html(res);
	}
	else if(choice=="METROSTATIONS"){
		 $.each (data, function (key, value) {
			   // console.log(value);
			        res +=
			        '<tr>'+
			            '<td vocab="https://opendata.paris.fr/api/records/1.0/search/?dataset=velib-emplacement-des-stations" resource="'+value.id+'" typeof="PublicBicycleStation"><span property="stationName">'+value.name+'</span></td>'+
			            '<td resource="'+value.id+'"><span property="geo:lat">'+value.lat+'</span></td>'+
			            '<td resource="'+value.id+'"><span property="geo:lon">'+value.lon+'</span></td>'+
			            '<td  resource="'+value.id+'"><span property="onto:metronumber">'+value.number+'</span></td>'+
			        '</tr>';
			    });

	       $("#bicycleStations").hide();
	       $("#hospitals").hide();
	       $("#sncfStations").hide();
	       $("#busStations").hide();
	       $("#tramStations").hide();
	       
			    $('#metrotbody').html(res);
	}
    
    
}
</script>

<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/dt/dt-1.10.20/datatables.min.css"/>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.20/css/jquery.dataTables.min.css"/>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.20/css/dataTables.bootstrap.min.css"/>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/responsive/2.2.3/css/responsive.bootstrap.min.css"/>
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<style type="text/css">
.table .thead-dark th {
    color: #330000;
    background-color: #CCCCFF;
    }

.error {
  color: red;
  margin-left: 5px;
}

label.error {
  display: inline;
}
#footer{
    background: #CCCCFF;
}
.nav-item{
    text-transform: uppercase;
}
</style>
</head>
<body prefix="geo: https://www.w3.org/2003/01/geo/wgs84_pos# onto: http://www.semanticweb.org/emse/ontologies/2020/11/city.owl#">


<nav class="navbar navbar-inverse">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="#"><h1>Lyon City Guide</h1></a>
        </div>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav mr-auto">
      <li class="nav-item active">
        <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="http://localhost:8080/bicycleSearch">Search Live Bicycle</a>
     <!-- </li>
       <li class="nav-item">
        <a class="nav-link" href="http://localhost:8080/poi">Point of Interest</a>
      </li> --> 
    </ul>
  </div>
</nav>
    </div>
</nav>

<div class="container">
    <div class="starter-template">
<!--        <h2>Message: ${message}</h2>-->
        <section id="error-msg" class="error"></section>
        <div class="card">
            <div class="card-body">
                <form class = "form-horizontal" id="search-form">
                    <div class="form-group row">
                        <label for="choice" class="col-sm-3 control-label">Select your choice<span
                                class="text-danger">*</span></label>
                        <div class="col-sm-7">
                            <select class="form-control" id="choice" name="choice" required>
                                <option value="0">Select your choice</option>
                                <option value="HOSPITALS">HOSPITALS</option>
                                <option value="BUSSTATIONS">BUS STATIONS</option>
                                <option value="TRAMSTATIONS">TRAM STATIONS</option>
                                <option value="BICYCLESTATIONS">BICYCLE STATIONS</option>
                                <option value="SNCFSTATIONS">SNCF STATIONS</option>
                                <option value="METROSTATIONS">METRO STATIONS</option>
                            </select>
                        </div>

                        <div class="col-sm-2">
                            <button id="btn-search" type="submit" class="btn btn-primary float-right">Search</button>
                        </div>
                    </div>
                </form>
                <br/>
                <div class="row" id="bicycleStations">
                    <table id="bicycleStationsTable" class="table table-striped table-bordered dt-responsive nowrap"">
                        <thead class="thead-dark">
                        <tr>
                            <th>Station Name</th>
                            <th>Latitude</th>
                            <th>Longitude</th>
                            <th>Capacity</th>
                        </tr>
                        </thead>
                        <tbody id="bicycletbody"></tbody>
                    </table>
                </div>
                 <br/>
                <div class="row" id="hospitals">
                    <table id="hospitalsTable" class="table table-striped table-bordered dt-responsive nowrap"">
                        <thead class="thead-dark">
                        <tr>
                            <th>Category</th>
                            <th>Latitude</th>
                            <th>Longitude</th>
                            <th>Address</th>
                            <th>Telephone</th>
                        </tr>
                        </thead>
                        <tbody id="hospitaltbody"></tbody>
                    </table>
                </div>
                 <br/>
                <div class="row" id="busStations">
                    <table id="busTable" class="table table-striped table-bordered dt-responsive nowrap"">
                        <thead class="thead-dark">
                        <tr>
                            <th>Bus Stop</th>
                            <th>Latitude</th>
                            <th>Longitude</th>
                            <th>Bus Number</th>                            
                        </tr>
                        </thead>
                        <tbody id="bustbody"></tbody>
                    </table>
                </div>
                  <br/>
                <div class="row" id="tramStations">
                    <table id="tramsTable" class="table table-striped table-bordered dt-responsive nowrap"">
                        <thead class="thead-dark">
                        <tr>
                            <th>Tram Stop</th>
                            <th>Latitude</th>
                            <th>Longitude</th>
                            <th>Tram Line</th>
                        </tr>
                        </thead>
                        <tbody id="tramtbody"></tbody>
                    </table>
                </div>
                  <br/>
                <div class="row" id="metroStations">
                    <table id="metroTable" class="table table-striped table-bordered dt-responsive nowrap"">
                        <thead class="thead-dark">
                        <tr>
                            <th>Metro Stop</th>
                            <th>Latitude</th>
                            <th>Longitude</th>
                            <th>Metro Line</th>
                        </tr>
                        </thead>
                        <tbody id="metrotbody"></tbody>
                    </table>
                </div>
                <div class="row" id="sncfStations">
                    <table id="sncfTable" class="table table-striped table-bordered dt-responsive nowrap"">
                        <thead class="thead-dark">
                        <tr>
                            <th>SNCFStationName</th>
                            <th>Latitude</th>
                            <th>Longitude</th>
                            <th>Arrival Time</th>
                            <th>Departure Time</th>
                            <th>Has Escalator</th>
                            <th>Has Elevator</th>
                        </tr>
                        </thead>
                        <tbody id="sncftbody"></tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<br/><br/>
<div class = "container">
    <footer class="page-footer font-small blue fixed-bottom">
        <div id="footer" class="footer-copyright text-center py-3">© 2020 Copyright: Semantic Web
        </div>
    </footer>
</div>

</body>

</html>
