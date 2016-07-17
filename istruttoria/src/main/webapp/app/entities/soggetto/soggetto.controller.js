(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .controller('SoggettoController', SoggettoController);

    SoggettoController.$inject = ['$scope', '$state', '$window', 'Soggetto', 'SoggettoSearch', 'SoggettoReport'];

    function SoggettoController ($scope, $state, $window, Soggetto, SoggettoSearch, SoggettoReport) {
        var vm = this;
        
        vm.soggettos = [];
        vm.search = search;
        vm.report = report;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Soggetto.query(function(result) {
                vm.soggettos = result;
            });
        }

        function search () {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            SoggettoSearch.query({query: vm.searchQuery}, function(result) {
                vm.soggettos = result;
            });
        }    
        
        function report () {
        	SoggettoReport.generate({}, function(result) {
        	    // la redirect non viene eseguita
        	    $window.location.href = '/content/reports/report.pdf';
        	});
        }    
        
    }
})();
