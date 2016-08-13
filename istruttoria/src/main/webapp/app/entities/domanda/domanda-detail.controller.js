(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .controller('DomandaDetailController', DomandaDetailController);

    DomandaDetailController.$inject = ['$scope','$window', '$rootScope', '$stateParams', 'entity', 'Domanda', 'Soggetto', 'AlertService'];

    function DomandaDetailController($scope,$window, $rootScope, $stateParams, entity, Domanda, Soggetto, AlertService) {
    	
        var vm = this;

        vm.domanda = entity;
        vm.chkLst = chkLst;

        var unsubscribe = $rootScope.$on('istruttoriaApp:domandaUpdate', function(event, result) {
            vm.domanda = result;
        });
        $scope.$on('$destroy', unsubscribe);
    
        function chkLst() {
        	var url = 'api/domanda/detail/report/' + vm.domanda.id;
            $window.open(url);
    	}
    }    
})();
