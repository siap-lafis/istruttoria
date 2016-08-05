(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .controller('DomandaDetailController', DomandaDetailController);

    DomandaDetailController.$inject = ['$scope','$window', '$rootScope', '$stateParams', 'entity', 'Domanda', 'Soggetto','DomandaDetailReport','AlertService'];

    function DomandaDetailController($scope,$window, $rootScope, $stateParams, entity, Domanda, Soggetto,DomandaDetailReport,AlertService) {
        var vm = this;

        vm.domanda = entity;
        vm.chkLst = chkLst;

        var unsubscribe = $rootScope.$on('istruttoriaApp:domandaUpdate', function(event, result) {
            vm.domanda = result;
        });
        $scope.$on('$destroy', unsubscribe);
    
        function chkLst(onError) {
        	DomandaDetailReport.generate(vm.domanda, function(result) {
        		// la redirect non viene eseguita
        		//$window.location.href = '/content/reports/reportChkLst.pdf';   
        		var fileName = result.name; 
        		var message = result.message;
        		if (fileName == null) {
        			onError('istruttoriaApp.domanda'+'.'+message);
        			return;
        		}
        		$window.open('/content/reports/'+fileName+'.pdf', '_blank', 'fullscreen=yes'); 
    	});
        function onError(error) {
              AlertService.error(error);
            }
        	
    }    
}
})();
