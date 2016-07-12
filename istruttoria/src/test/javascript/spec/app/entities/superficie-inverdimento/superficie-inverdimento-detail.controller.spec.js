'use strict';

describe('Controller Tests', function() {

    describe('SuperficieInverdimento Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockSuperficieInverdimento, MockDomanda;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockSuperficieInverdimento = jasmine.createSpy('MockSuperficieInverdimento');
            MockDomanda = jasmine.createSpy('MockDomanda');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'SuperficieInverdimento': MockSuperficieInverdimento,
                'Domanda': MockDomanda
            };
            createController = function() {
                $injector.get('$controller')("SuperficieInverdimentoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'istruttoriaApp:superficieInverdimentoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
