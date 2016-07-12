'use strict';

describe('Controller Tests', function() {

    describe('CapoPagato Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCapoPagato, MockPagamento;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCapoPagato = jasmine.createSpy('MockCapoPagato');
            MockPagamento = jasmine.createSpy('MockPagamento');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'CapoPagato': MockCapoPagato,
                'Pagamento': MockPagamento
            };
            createController = function() {
                $injector.get('$controller')("CapoPagatoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'istruttoriaApp:capoPagatoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
