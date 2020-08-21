import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IncomingChatMessageComponent } from './incoming-chat-message.component';

describe('IncomingChatMessageComponent', () => {
  let component: IncomingChatMessageComponent;
  let fixture: ComponentFixture<IncomingChatMessageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IncomingChatMessageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IncomingChatMessageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
