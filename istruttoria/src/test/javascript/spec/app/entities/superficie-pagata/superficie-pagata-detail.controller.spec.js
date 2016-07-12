'use strict';

describe('Controller Tests', function() {

    describe('SuperficiePagata Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockSuperficiePagata, MockPagamento;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockSuperficiePagata = jasmine.createSpy('MockSuperficiePagata');
            MockPagamento = jasmine.createSpy('MockPagamento');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'SuperficiePagata': MockSuperficiePagata,
                'Pagamento': MockPagamento
            };
            createController = function() {
                $injector.get('$controller')("SuperficiePagataDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'istruttoriaApp:superficiePagataUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
