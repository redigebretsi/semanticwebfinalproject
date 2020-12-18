<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script><script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
<script type="text/javascript" src="https://cdn.datatables.net/1.10.20/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/1.10.20/js/dataTables.bootstrap.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/responsive/2.2.3/js/dataTables.responsive.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/responsive/2.2.3/js/responsive.bootstrap.min.js"></script>


<script type="text/javascript">
   var museumsTable;
   $(function () {
        $("#bicycleStations").hide();
         museumsTable = $('#museumsTable').DataTable({
                    "sPaginationType": "full_numbers",
                    "bDestroy": true,
                    'bAutoWidth': false,
                    'searching': false,
                    'info': false,
                    'paging': false,
                    "columnDefs": [
                        {width: "90px", "targets": [0]},
                        {width: "100px", "targets": [1]},
                        {width: "80px", "targets": [2]},
                        {width: "90px", "targets": [3]},
                        {className: "text-center", "targets": [1, 2, 3]},
                        {className: "action-btn-column", "targets": [4]}
                    ],
                    "order": [
                        [0, "asc"]
                    ],
                    responsive: true
                });


    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/api/getMuseums/",
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            setTableRows(data);
            $("#btn-search").prop("disabled", false);
        },
        error: function (e) {
            var json = "<h4>Ajax Response</h4><pre>"+ e.responseText + "</pre>";
            $('#error-msg').innerHTML=json;
            console.log("ERROR : ", e);
        }
    });
});

function setTableRows(data){
    var res='';
    $.each (data, function (key, value) {
        res +=
        '<tr>'+
            '<td vocab="https://opendata.paris.fr/api/records/1.0/search/?dataset=velib-emplacement-des-stations" resource="'+value.museumLabel+'" typeof="Museum"><span property="Museum">'+value.museumLabel+'</span></td>'+
            '<td resource="'+value.museumLabel+'"><span property="museumDescription">'+value.museumDescription+'</span></td>'+
            '<td resource="'+value.museumLabel+'"><span property="museumLabel">'+value.museumLabel+'</span></td>'+
            '<td resource="'+value.museumLabel+'"><span property="ville">'+value.ville+'</span></td>'+
            '<td resource="'+value.museumLabel+'"><span property="coord">'+value.coord+'</span></td>'+
            
        '</tr>';
    });
    $('tbody').html(res);
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
      <li class="nav-item ">
        <a class="nav-link" href="http://localhost:8080">Home</span></a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="http://localhost:8080/bicycleSearch">Search Live Bicycle</a>
      </li>
       <li class="nav-item active">
        <a class="nav-link" href="http://localhost:8080/museum">Museums<span class="sr-only">(current)</span></a>
      </li>
    </ul>
  </div>
</nav>
    </div>
</nav>

<div class="container">
    <div class="starter-template">
        <section id="error-msg" class="error"></section>
        <div class="card">
            <div class="card-body">
                <div class="row" id="museums">
                    <table id="museumsTable" class="table table-striped table-bordered dt-responsive nowrap"">
                        <thead class="thead-dark">
                        <tr>
                            <th>Name</th>
                            <th>Description</th>
                            <th>IdLabel</th>
                            <th>Ville</th>
                            <th>Coordinates</th>                      
                        </tr>
                        </thead>
                        <tbody></tbody>
                    </table>
                </div>
            </div>
        </div
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
