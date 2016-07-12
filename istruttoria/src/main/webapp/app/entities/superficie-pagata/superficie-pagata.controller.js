(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .controller('SuperficiePagataController', SuperficiePagataController);

    SuperficiePagataController.$inject = ['$scope', '$state', 'SuperficiePagata', 'SuperficiePagataSearch'];

    function SuperficiePagataController ($scope, $state, SuperficiePagata, SuperficiePagataSearch) {
        var vm = this;
        
        vm.superficiePagatas = [];
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            SuperficiePagata.query(function(result) {
                vm.superficiePagatas = result;
            });
        }

        function search () {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            SuperficiePagataSearch.query({query: vm.searchQuery}, function(result) {
                vm.superficiePagatas = result;
            });
        }    }
})();
