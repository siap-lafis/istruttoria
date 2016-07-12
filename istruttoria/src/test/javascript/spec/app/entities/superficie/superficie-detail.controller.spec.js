'use strict';

describe('Controller Tests', function() {

    describe('Superficie Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockSuperficie, MockDomanda;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockSuperficie = jasmine.createSpy('MockSuperficie');
            MockDomanda = jasmine.createSpy('MockDomanda');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Superficie': MockSuperficie,
                'Domanda': MockDomanda
            };
            createController = function() {
                $injector.get('$controller')("SuperficieDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'istruttoriaApp:superficieUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
