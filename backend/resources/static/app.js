window.onload = function () {
  var apiUrl = '/bikemifeedback/api/v1'
  ko.applyBindings(new VmBikeMiFeeback(), document.getElementById("app"));
  moment().locale('it');

  function VmBikeMiFeeback() {
    var self = this;

    this.loadingData = ko.observable(true);
    this.state       = ko.observable({
      moment: null,
      stations: []
    });
    this.query = ko.observable("");

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
