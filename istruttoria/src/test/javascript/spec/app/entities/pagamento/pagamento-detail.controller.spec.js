'use strict';

describe('Controller Tests', function() {

    describe('Pagamento Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPagamento, MockElencoPagamento;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPagamento = jasmine.createSpy('MockPagamento');
            MockElencoPagamento = jasmine.createSpy('MockElencoPagamento');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Pagamento': MockPagamento,
                'ElencoPagamento': MockElencoPagamento
            };
            createController = function() {
                $injector.get('$controller')("PagamentoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'istruttoriaApp:pagamentoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
