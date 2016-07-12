(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .controller('CapoPagatoController', CapoPagatoController);

    CapoPagatoController.$inject = ['$scope', '$state', 'CapoPagato', 'CapoPagatoSearch'];

    function CapoPagatoController ($scope, $state, CapoPagato, CapoPagatoSearch) {
        var vm = this;
        
        vm.capoPagatoes = [];
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            CapoPagato.query(function(result) {
                vm.capoPagatoes = result;
            });
        }

        function search () {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            CapoPagatoSearch.query({query: vm.searchQuery}, function(result) {
                vm.capoPagatoes = result;
            });
        }    }
})();
