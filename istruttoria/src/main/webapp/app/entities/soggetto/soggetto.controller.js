(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .controller('SoggettoController', SoggettoController);

    SoggettoController.$inject = ['$scope', '$state', 'Soggetto', 'SoggettoSearch'];

    function SoggettoController ($scope, $state, Soggetto, SoggettoSearch) {
        var vm = this;
        
        vm.soggettos = [];
        vm.search = search;
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
        }    }
})();
