window.onload = function () {
  var apiUrl = '/bikemifeedback/api/v1'
  var vm = new VmBikeMiFeeback();
  ko.applyBindings(vm, document.getElementById("app"));

  function VmBikeMiFeeback() {
    var self = this;

    this.loadingData = ko.observable(true);
    this.state       = ko.observable({
      moment: null,
      stations: []
    });
    this.query = ko.observable("");
    this.activeTab = ko.observable("home");
    this.firstTimeMap = ko.observable(true);
    this.map = ko.observable(null);
    this.markers = ko.observable([]);

    this.momentHr = ko.computed(function () {
      var m = self.state().moment;
      return m == null ? "--" : m.locale('it').format("dddd, D MMMM YYYY, HH:mm:ss");
    });

    this.stations = ko.computed(function () {
      var q = self.query();
      if (q == "") {
        return self.state().stations;
      } else {
        return self.state().stations.filter(s => s.name.toLowerCase().indexOf(q) != -1);
      }
    });

    this.activeMap = function () {
      this.activeTab("map");
      if (this.map() == null) {
        this.map(new google.maps.Map(document.getElementById('map'), {
          zoom: 12,
          center: {lat: 45.464201, lng: 9.189982}
        }));
        if (!this.loadingData()) {
          this.markers(_.map(this.state().stations, s => this.addMarker(s)));
        }
      }
    }

    this.addMarker = function(s) {
      var contentString = '<div class="info-window">' +
                            "<div><strong>" +
                            s.code + " -- " + s.name + " " +
                            "</strong></div>" +
                            "<div>" +
                             "B <b>" + s.bikes + "</b> E <b>" + s.ebikes + "</b> S <b>" + s.racks + "</b>" +
                             "</div>" +
                          '</div>';
      var iconUrl = 'http://localhost:8083/bikemifeedback/static/bicycle_green.png';
      if (s.bikes + s.ebikes == 0) {
        iconUrl = 'http://localhost:8083/bikemifeedback/static/bicycle_red.png'
      } else if (s.bikes + s.ebikes < 5) {
        iconUrl = 'http://localhost:8083/bikemifeedback/static/bicycle_orange.png'
      }
      var icon = {
        url: iconUrl,
        size: new google.maps.Size(30, 30)
      };
      var m = new google.maps.Marker({
        map: this.map(),
        position: s.position,
        icon: icon
      });
      var infowindow = new google.maps.InfoWindow({
        content: contentString
      });
      m.addListener('click', function() {
        infowindow.open(map, m);
      });
      return m;
    };

    this.activeHome = function () {
      this.activeTab("home");
    }

    this.init = function () {
      self.loadingData(true);
      $.get(
        apiUrl + "/stations/current",
        function (data) {
          self.loadingData(false);
          self.state({
            moment: moment(data.moment),
            stations: data.stations
          });
        }
      );

    };

    this.init();

  }
}
